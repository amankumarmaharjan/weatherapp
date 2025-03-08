package com.weather.application;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Note that with Spring boot 3.0, the @EnableBatchProcessing is discouraged.
 * Also, JobBuilderFactory and StepBuilderFactory are deprecated and it is recommended to use JobBuilder and StepBuilder classes with the name of the job or step builder.
 */
@Configuration
public class BatchConfiguration {

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public VirtualThreadTaskExecutor taskExecutor() {
        return new VirtualThreadTaskExecutor("virtual-thread-executor");
    }

    /** the reader bean defined above looks for a file called coffee-list.csv
     * and parses each line item into a Coffee object.**/
    @Bean
    public FlatFileItemReader<Coffee> reader() {
        return new FlatFileItemReaderBuilder<Coffee>().name("coffeeItemReader")
            .resource(new ClassPathResource(fileInput))
            .delimited()
            .names(new String[] { "brand", "origin", "characteristics" })
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Coffee>() {{
                setTargetType(Coffee.class);
             }})
            .build();
    }

    @Bean
    public CoffeeItemProcessor processor() {
        return new CoffeeItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Coffee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Coffee>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener) /** JobCompletionNotificationListener, which we’ll use to get notified when the job completes.**/
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Coffee> writer, VirtualThreadTaskExecutor taskExecutor) {
        return new StepBuilder("step1", jobRepository)
            .<Coffee, Coffee> chunk(10, transactionManager)/** First, we configure our step so that it’ll write up to ten records at a time using the chunk(10) declaration.**/
            .reader(reader()) /**Then we read in the coffee data using our reader bean, which we set using the reader method.**/
            .processor(processor()) /** Next, we pass each of our coffee items to a custom processor where we apply some custom business logic.**/
            .writer(writer) /** Finally, we write each coffee item to the database using the writer we saw previously.**/
            .taskExecutor(taskExecutor) /**running concurrent steps or parallelizing the execution of a single step. **/
            .build();
    }

}
