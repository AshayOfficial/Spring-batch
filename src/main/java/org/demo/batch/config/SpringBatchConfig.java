package org.demo.batch.config;

import lombok.RequiredArgsConstructor;
import org.demo.batch.entity.BusinessEmploymentEntity;
import org.demo.batch.repository.SpringBatchRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.net.MalformedURLException;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {
    @Autowired
    private SpringBatchRepository springBatchRepository;
    @Value("${file.input}")
    private String fileLocation;
    @Autowired
    private DataSource dataSource;

    @Bean
    public FlatFileItemReader<BusinessEmploymentEntity> reader() {
        FlatFileItemReader<BusinessEmploymentEntity> fileItemReader = new FlatFileItemReader<>();
        ClassPathResource resource = new ClassPathResource("data.csv");
        fileItemReader.setResource(resource);
        fileItemReader.setName("csvReader");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setLineMapper(lineMapper());
        return fileItemReader;
    }

    @Bean
    public BusinessEmploymentProcessor processor() {
        return new BusinessEmploymentProcessor();
    }

    @Bean
    public ItemWriter<BusinessEmploymentEntity> writer() {
        RepositoryItemWriter<BusinessEmploymentEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(this.springBatchRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step(JobRepository jobRepository) throws MalformedURLException {
        return new StepBuilder("File_Reader_Step", jobRepository)
                .<BusinessEmploymentEntity, BusinessEmploymentEntity>chunk(100, this.platformTransactionManager())
                .reader(this.reader())
                .processor(this.processor())
                .writer(this.writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository) throws MalformedURLException {
        return new JobBuilder("Job-1", jobRepository)
                .start(step(jobRepository))
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }

    private LineMapper<BusinessEmploymentEntity> lineMapper() {
        DefaultLineMapper<BusinessEmploymentEntity> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("Series_reference", "Period", "Data_value", "Suppressed", "STATUS", "UNITS", "Magnitude", "Subject", "Group", "Series_title_1", "Series_title_2", "Series_title_3");

        BeanWrapperFieldSetMapper<BusinessEmploymentEntity> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(BusinessEmploymentEntity.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }
}
