package ir.sharifi.soroush.soroush_test_project.detergent.tests;

import com.sun.glass.ui.Application;
import ir.sharifi.soroush.soroush_test_project.H2TestProfileJPAConfig;
import ir.sharifi.soroush.soroush_test_project.TestConfigs;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentInsertDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentOutDto;
import ir.sharifi.soroush.soroush_test_project.detergent.dto.DetergentUpdateDto;
import ir.sharifi.soroush.soroush_test_project.detergent.model.Detergent;
import ir.sharifi.soroush.soroush_test_project.detergent.repo.DetergentRepository;
import ir.sharifi.soroush.soroush_test_project.detergent.service.DetergentServiceImpl;
import ir.sharifi.soroush.soroush_test_project.detergent.service.IDetergentService;
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
        Application.class,
        H2TestProfileJPAConfig.class,
        DetergentServiceImpl.class,
        TestConfigs.class
})
@ActiveProfiles("test")
class DetergentServiceImplTest {

    @Autowired
    IDetergentService detergentService;

    @Autowired
    DetergentRepository detergentRepository;
    private String name;
    private LocalDateTime bringInDate;
    private LocalDateTime bringOutDate;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private String producer;
    private int quantity;
    private Unit unit;
    private int isbn;
    private Detergent detergent;
    private DetergentOutDto insert;

    @BeforeEach
    void setUp() {

        name = "name";
        bringInDate = LocalDateTime.now();
        bringOutDate = LocalDateTime.now().plusDays(1);
        productionDate = LocalDate.now().minusDays(10);
        expirationDate = LocalDate.now().plusYears(2);
        producer = "producer";
        quantity = 1;
        unit = Unit.KILOGRAM;
        isbn = 123456789;
        Detergent build = Detergent.DetergentBuilder()
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
        detergent = detergentRepository.save(build);

    }

    @AfterEach
    void tearDown() {
        detergentRepository.delete(detergent);
        if (insert != null) {
            detergentService.delete(insert.getId());
        }
    }

    @Test
    void findById() {
        DetergentOutDto byId = detergentService.findById(detergent.getId());
        assertNotNull(byId);
        assertEquals(detergent.getId(), byId.getId());
        assertNotNull(byId.getBringInDate());
        assertEquals(detergent.getBringInDate(), byId.getBringInDate());
        assertNotNull(byId.getBringOutDate());
        assertEquals(detergent.getBringOutDate(), byId.getBringOutDate());
        assertNotNull(byId.getExpirationDate());
        assertEquals(detergent.getExpirationDate(), byId.getExpirationDate());
        assertNotNull(byId.getProductionDate());
        assertEquals(detergent.getProductionDate(), byId.getProductionDate());
        assertNotNull(byId.getProducer());
        assertEquals(detergent.getProducer(), byId.getProducer());
        assertNotNull(byId.getName());
        assertEquals(detergent.getName(), byId.getName());
        assertEquals(detergent.getIsbn(), byId.getIsbn());
        assertEquals(detergent.getQuantity(), byId.getQuantity());
        assertNotNull(byId.getUnit());
        assertEquals(detergent.getUnit(), byId.getUnit());

        assertEquals(detergent.getType(), byId.getType());


        assertThrows(EntityNotFoundException.class, () -> detergentService.findById(0L));


    }

    @Test
    void insert() {

        DetergentInsertDto newInsertDto = DetergentInsertDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.plusYears(2))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().plusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.FOODSTUFF)
                .build();
        insert = detergentService.insert(newInsertDto);

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

        assertEquals(insert.getType(), ProductType.DETERGENT);
    }

    @Test
    void checkProductDatesLogic() {
        DetergentInsertDto detergentInsertDto = DetergentInsertDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.minusDays(5))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().minusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.FOODSTUFF)
                .build();

        detergentInsertDto.setBringInDate(LocalDateTime.now());
        detergentInsertDto.setProductionDate(LocalDate.now().plusDays(4));
        ConstraintViolationException thrown = assertThrows(ConstraintViolationException.class, () -> detergentService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.production.date.is.after.bringIn.date"));


        detergentInsertDto.setExpirationDate(LocalDate.now());
        detergentInsertDto.setBringInDate(LocalDateTime.now().plusDays(4));
        thrown = assertThrows(ConstraintViolationException.class,()->detergentService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.expire.date.is.before.bringIn.date"));

        detergentInsertDto.setBringInDate(LocalDateTime.now());
        detergentInsertDto.setBringOutDate(LocalDateTime.now().minusDays(2));
        thrown = assertThrows(ConstraintViolationException.class,()->detergentService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.bringIn.date.is.after.bringOut.date"));

        detergentInsertDto.setProductionDate(LocalDate.now());
        detergentInsertDto.setExpirationDate(LocalDate.now().minusDays(5));
        thrown = assertThrows(ConstraintViolationException.class,()->detergentService.insert(detergentInsertDto));
        assertTrue(thrown.getMessage().contains("product.production.date.is.after.expiration.date"));
    }

    @Test
    void update() {


        DetergentInsertDto newInsertDto = DetergentInsertDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.plusYears(2))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().plusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.FOODSTUFF)
                .build();
        insert = detergentService.insert(newInsertDto);

        bringInDate = bringInDate.minusYears(2);
        name = "updateName";
        producer = "updateProducer";
        quantity = 23;
        unit = Unit.NUMBER;
        isbn  = 987654;
        DetergentUpdateDto updateDto = DetergentUpdateDto.builder()
                .name(name)
                .bringInDate(bringInDate)
                .bringOutDate(bringInDate.plusYears(2))
                .productionDate(bringInDate.toLocalDate().minusDays(5))
                .expirationDate(bringInDate.toLocalDate().plusDays(10))
                .producer(producer)
                .quantity(quantity)
                .unit(unit)
                .isbn(isbn)
                .type(ProductType.FOODSTUFF)
                .build();
        updateDto.setId(insert.getId());

        insert = detergentService.update(updateDto);

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

        assertEquals(insert.getType(), ProductType.DETERGENT);

    }

    @Test
    void delete() {

    }

    @Test
    void getModels() {
        assertTrue(detergentService.getModels().size()>0);
    }
}