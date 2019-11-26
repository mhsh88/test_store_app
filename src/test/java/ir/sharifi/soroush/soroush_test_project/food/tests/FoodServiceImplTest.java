package ir.sharifi.soroush.soroush_test_project.food.tests;

import ir.sharifi.soroush.soroush_test_project.H2TestProfileJPAConfig;
import ir.sharifi.soroush.soroush_test_project.TestConfigs;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInsertDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodOutDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodUpdateDto;
import ir.sharifi.soroush.soroush_test_project.food.model.FoodStuff;
import ir.sharifi.soroush.soroush_test_project.food.repo.FoodRepository;
import ir.sharifi.soroush.soroush_test_project.food.service.FoodServiceImpl;
import ir.sharifi.soroush.soroush_test_project.food.service.IFoodService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        H2TestProfileJPAConfig.class,
        FoodServiceImpl.class,
        TestConfigs.class
})
@ActiveProfiles("test")
class FoodServiceImplTest {

    @Autowired
    IFoodService foodService;

    @Autowired
    FoodRepository foodRepository;
    private String name;
    private LocalDateTime bringInDate;
    private LocalDateTime bringOutDate;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private String producer;
    private int quantity;
    private Unit unit;
    private int isbn;
    private FoodStuff foodStuff;
    private FoodOutDto insert;

    @BeforeEach
    void setUp() {

        name = "name";
        bringInDate = LocalDateTime.now();
        bringOutDate = LocalDateTime.now().plusDays(1);
        productionDate = LocalDate.now().minusDays(10);
        expirationDate = LocalDate.now().plusYears(2);
        producer = "producer";
        quantity = 5;
        unit = Unit.BATCH;
        isbn = 123456789;
        FoodStuff build = FoodStuff.FoodBuilder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringOutDate)
                .productionDate(productionDate)
                .expirationDate(expirationDate)
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .build();
        foodStuff = foodRepository.save(build);

    }

    @AfterEach
    void tearDown() {
        foodRepository.delete(foodStuff);
        if (insert != null) {
            foodService.delete(insert.getId());
        }
    }

    @Test
    void findById() {
        FoodOutDto byId = foodService.findById(foodStuff.getId());
        assertNotNull(byId);
        assertEquals(foodStuff.getId(), byId.getId());
        assertNotNull(byId.getBringInDate());
        assertEquals(foodStuff.getBringInDate(), byId.getBringInDate());
        assertNotNull(byId.getBringOutDate());
        assertEquals(foodStuff.getBringOutDate(), byId.getBringOutDate());
        assertNotNull(byId.getExpirationDate());
        assertEquals(foodStuff.getExpirationDate(), byId.getExpirationDate());
        assertNotNull(byId.getProductionDate());
        assertEquals(foodStuff.getProductionDate(), byId.getProductionDate());
        assertNotNull(byId.getProducer());
        assertEquals(foodStuff.getProducer(), byId.getProducer());
        assertNotNull(byId.getName());
        assertEquals(foodStuff.getName(), byId.getName());
        assertEquals(foodStuff.getIsbn(), byId.getIsbn());
        assertEquals(foodStuff.getQuantity(), byId.getQuantity());
        assertNotNull(byId.getUnit());
        assertEquals(foodStuff.getUnit(), byId.getUnit());

        assertEquals(foodStuff.getType(), byId.getType());


        assertThrows(EntityNotFoundException.class, () -> foodService.findById(0L));


    }

    @Test
    void insert() {

        FoodInsertDto newInsertDto = FoodInsertDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.plusYears(2))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().plusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.DETERGENT)
                .build();
        insert = foodService.insert(newInsertDto);

        assertNotNull(insert);
        assertNotNull(insert.getId());
        assertNotNull(insert.getBringInDate());
        assertEquals(newInsertDto.getBringInDate(), insert.getBringInDate());
        assertNotNull(insert.getBringOutDate());
        assertEquals(newInsertDto.getBringOutDate(), insert.getBringOutDate());
        assertNotNull(insert.getExpirationDate());
        assertEquals(newInsertDto.getExpirationDate(), insert.getExpirationDate());
        assertNotNull(insert.getProductionDate());
        assertEquals(newInsertDto.getProductionDate(), insert.getProductionDate());
        assertNotNull(insert.getProducer());
        assertEquals(newInsertDto.getProducer(), insert.getProducer());
        assertNotNull(insert.getName());
        assertEquals(newInsertDto.getName(), insert.getName());
        assertEquals(newInsertDto.getIsbn(), insert.getIsbn());
        assertEquals(newInsertDto.getQuantity(), insert.getQuantity());
        assertNotNull(insert.getUnit());
        assertEquals(newInsertDto.getUnit(), insert.getUnit());

        assertEquals(newInsertDto.getType(), insert.getType());

        assertEquals(insert.getType(), ProductType.FOODSTUFF);
    }

    @Test
    void checkProductDatesLogic() {
        FoodInsertDto detergentInsertDto = FoodInsertDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.minusDays(5))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().minusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .build();

        detergentInsertDto.setBringInDate(LocalDateTime.now());
        detergentInsertDto.setProductionDate(LocalDate.now().plusDays(4));
        ConstraintViolationException thrown = assertThrows(ConstraintViolationException.class, () -> foodService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.production.date.is.after.bringIn.date"));


        detergentInsertDto.setExpirationDate(LocalDate.now());
        detergentInsertDto.setBringInDate(LocalDateTime.now().plusDays(4));
        thrown = assertThrows(ConstraintViolationException.class,()-> foodService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.expire.date.is.before.bringIn.date"));

        detergentInsertDto.setBringInDate(LocalDateTime.now());
        detergentInsertDto.setBringOutDate(LocalDateTime.now().minusDays(2));
        thrown = assertThrows(ConstraintViolationException.class,()-> foodService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.bringIn.date.is.after.bringOut.date"));

        detergentInsertDto.setProductionDate(LocalDate.now());
        detergentInsertDto.setExpirationDate(LocalDate.now().minusDays(5));
        thrown = assertThrows(ConstraintViolationException.class,()-> foodService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.production.date.is.after.expiration.date"));
    }

    @Test
    void update() {


        FoodInsertDto newInsertDto = FoodInsertDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.plusYears(2))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().plusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.DETERGENT)
                .build();
        insert = foodService.insert(newInsertDto);

        bringInDate = bringInDate.minusYears(2);
        name = "updateName";
        producer = "updateProducer";
        quantity = 23;
        unit = Unit.NUMBER;
        isbn  = 987654;
        FoodUpdateDto updateDto = FoodUpdateDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.plusYears(2))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().plusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.DETERGENT)
                .build();
        updateDto.setId(insert.getId());

        insert = foodService.update(updateDto);

        assertNotNull(insert);
        assertNotNull(insert.getId());
        assertEquals(insert.getId(),updateDto.getId());
        assertNotNull(insert.getBringInDate());
        assertEquals(updateDto.getBringInDate(), insert.getBringInDate());
        assertNotNull(insert.getBringOutDate());
        assertEquals(updateDto.getBringOutDate(), insert.getBringOutDate());
        assertNotNull(insert.getExpirationDate());
        assertEquals(updateDto.getExpirationDate(), insert.getExpirationDate());
        assertNotNull(insert.getProductionDate());
        assertEquals(updateDto.getProductionDate(), insert.getProductionDate());
        assertNotNull(insert.getProducer());
        assertEquals(updateDto.getProducer(), insert.getProducer());
        assertNotNull(insert.getName());
        assertEquals(updateDto.getName(), insert.getName());
        assertEquals(updateDto.getIsbn(), insert.getIsbn());
        assertEquals(updateDto.getQuantity(), insert.getQuantity());
        assertNotNull(insert.getUnit());
        assertEquals(updateDto.getUnit(), insert.getUnit());

        assertEquals(updateDto.getType(), insert.getType());

        assertEquals(insert.getType(), ProductType.FOODSTUFF);

    }

    @Test
    void delete() {

    }

    @Test
    void getModels() {
        assertTrue(foodService.getModels().size()>0);
    }
}