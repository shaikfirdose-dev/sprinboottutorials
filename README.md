# sprinboottutorials
@Week-2 Homework

1. Make a list of all the annotations we have seen so far in the Spring Web Framework.
   @SpringBoootApplication
   @Configuration
   @EnableAutoCOnfiguration
   @ComponentScan
   @RestController
   @RequestMapping
   @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping
   @Component, @Bean, @Service, @Repository
   @Scope, @PostContruct, @PreDestroy
   @ConditionalOnProperty
   @Autowired
   @Primary
   @Entity, @Table, @Data, @Getter, @Setter, @Id, @GeneratedValue, @Column
   @Valid, @NotNull, @NotBlank, @NotEmpty, @Pattern, @Max, @Min, @Digit, @DecimalMax,@DecimalMin, @Size, @Past, @PastOrPresent, @Future, @FutureOrPresent, @Email,@AssertTrue, @AssetFalse, @Positive
   @RestControllerAdvice, @ExceptionHandler
   @JsonFormat

2. Create the following REST endpoints for the following Entity
    Department-
    id-
    title-
    isActive-
   
    createdAt
    REST APIs:
    GET: /departments
    POST: /departments
    PUT: /departments
    DELETE: /departments
    GET: /departments/{id}

     created in practice project
   3. Write Exception Handling logic for the Department Entity.
      implemented in the code

   4. Add the appropriate fields in the Department and Employee Entities so that the following validations can be added: @Null, @NotNull, @AssertTrue, @AssertFalse, @Min, @Max, @DecimalMin, @DecimalMax, @Negative, @NegativeOrZero, @Positive, @PositiveOrZero, @Size, @Digits, @Past, @PastOrPresent, @Future, @FutureOrPresent, @Pattern, @Email, @CreditCardNumber, @URL   
