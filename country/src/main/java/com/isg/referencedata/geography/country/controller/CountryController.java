package com.isg.referencedata.geography.country.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.isg.referencedata.geography.country.entity.Country;
import com.isg.referencedata.geography.country.repository.CountryRepository;
import com.isg.referencedata.geography.country.response.ResponseBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.ReflectionUtils;

@RestController
@Validated
@Tag(name = "Country Controller", description = "Contains RestServices of Country")
public class CountryController {

    private static final Logger log = LogManager.getLogger(CountryController.class);

    @Autowired
    public CountryRepository countryrepo;

    @Autowired
    public ResponseBean response;

    public final MessageSource messageSource;

    public CountryController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostMapping(path = "/country", consumes = "application/JSon", produces = "application/Json")
    @Operation(summary = "Adds Country information")
    public ResponseEntity<Object> addCountry(@RequestBody @Valid Country country,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @Param("createdBy") String createdBy) {

        log.info("Start of Post request by -- " + createdBy);
        try {
            if (countryrepo.findById(country.getId()).isPresent()) {

                String descMessage = messageSource.getMessage("ERROR_DESC02", null, LocaleContextHolder.getLocale());
                String messageCode = messageSource.getMessage("ERROR_ID02", null, LocaleContextHolder.getLocale());

                response.setErrorCode(messageCode);
                response.setErrorDesc(descMessage);
                response.setData(null);
                log.error(descMessage + " at --" + country.getId() + " id");
                log.info("End of Post request by --" + createdBy);
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

            } else {

                country.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                country.setCreatedBy(createdBy);
                Country save = countryrepo.save(country);

                response.setErrorCode(null);
                response.setErrorDesc(null);
                response.setData(save);
                log.debug("Created -- " + save);
                log.info("End of Post request by --" + createdBy);

                return new ResponseEntity<Object>(response, HttpStatus.CREATED);

            }
        } catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/countries", produces = "application/Json")
    @Operation(summary = "Returns List of Country")
    public ResponseEntity<Object> getCountries(
           // @RequestHeader(name = "Accept-Language", required = false) Locale locale) {

        //log.info("Start of Get request to fetch list");

        List<Country> countrylist = countryrepo.findAll();
        //response.setErrorCode(null);
        //response.setErrorDesc(null);
        response.setData(countrylist);

        //log.info("End of Get request");

        return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

    @GetMapping(path = "/country/{id}")
    @Operation(summary = "Returns Country by its Id")
    public ResponseEntity<Object> getCountry(
            @PathVariable("id") @Parameter(description = "The Id of the Country to fetch.") int id,
            @RequestHeader(name = "Accept-Language", required = false) Locale loc) {

        log.info("Start of Get request to fetch country from -- " +id +" id");
        Optional<Country> country = countryrepo.findById(id);
        if (country.isPresent()) {

            response.setErrorCode(null);
            response.setErrorDesc(null);
            response.setData(country);

            log.info("End of Get request");
            return new ResponseEntity<Object>(response, HttpStatus.OK);

        } else {

            String descMessage = messageSource.getMessage("ERROR_DESC01", null, LocaleContextHolder.getLocale());
            String messageCode = messageSource.getMessage("ERROR_ID01", null, LocaleContextHolder.getLocale());
            response.setErrorCode(messageCode);
            response.setErrorDesc(descMessage +"-- "+id);
            log.error(descMessage);
            response.setData(null);
            log.info("End of Get request");

            return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/country/{id}")
    @Operation(summary = "Deletes Country by its Id")
    public ResponseEntity<Object> deleteCountry(
            @PathVariable("id") @Parameter(description = "The Id of the Country to delete.") int id,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @Param("modifiedBy") String modifiedBy) {

        log.info("Start of Delete request");
        if (countryrepo.findById(id).isPresent()) {

            log.debug("Country present at "+ id + " id is deleted by " + modifiedBy);

            countryrepo.deleteById(id);

            response.setErrorCode(null);
            response.setErrorDesc(null);
            response.setData("deleted");
            log.info("End of Delete request");
            return new ResponseEntity<Object>(response, HttpStatus.OK);

        } else {

            String descMessage = messageSource.getMessage("ERROR_DESC01", null, LocaleContextHolder.getLocale());
            String messageCode = messageSource.getMessage("ERROR_ID01", null, LocaleContextHolder.getLocale());
            response.setErrorCode(messageCode);
            response.setErrorDesc(descMessage);
            log.error(descMessage +"-- "+id);
            response.setData(null);
            log.info("End of Delete request");
            return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "/country/{id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Updates Country information ")
    public ResponseEntity<Object> updateCountry(@RequestBody @Valid Country country,
            @PathVariable("id") @Parameter(description = "The Id of the Country to update.") int id,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @Param("modifiedBy") String modifiedBy, @Param("createdBy") String createdBy) {

                log.info("Start of Put request");
                
        try {
            Optional<Country> optionalCountry = countryrepo.findById(id);
            if (optionalCountry.isPresent()) {

                Country cntry = optionalCountry.get();
                cntry.setModifiedBy(modifiedBy);
                cntry.setModifiedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                cntry.setId(id);
                cntry.setName(country.getName());
                cntry.setCode(country.getCode());
                countryrepo.save(cntry);

                response.setErrorCode(null);
                response.setErrorDesc(null);
                response.setData(cntry);
                log.debug("Updated Country present at "+id+ " id by "+ modifiedBy);
                log.info("End of Put request");
                return new ResponseEntity<Object>(response, HttpStatus.OK);

            } else {

                country.setId(id);
                country.setCreatedBy(createdBy);
                country.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                response.setErrorCode(null);
                response.setErrorDesc(null);
                response.setData(countryrepo.save(country));

                log.debug("New country created at "+ id+ " id by "+ createdBy);
                log.info("End of Put request");
                return new ResponseEntity<Object>(response, HttpStatus.OK);

            }
        } catch (Exception e) {

            String descMessage = messageSource.getMessage("ERROR_DESC01", null, LocaleContextHolder.getLocale());
            String messageCode = messageSource.getMessage("ERROR_ID01", null, LocaleContextHolder.getLocale());

            //need to work on this for validation exception pending  - 28-11-20222
            response.setErrorCode(messageCode);
            response.setErrorDesc(descMessage);
            log.error(descMessage);
            response.setData(null);
            log.error(e.getMessage());
            log.info("End of Put request");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping(path = "/country/{id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Updates Country information partially")
    public ResponseEntity<Object> partialUpdateCountry(@RequestBody @Valid Map<Object, Object> objectMap,
            @PathVariable("id") @Parameter(description = "The Id of the Country to Update.") int id,
            @RequestHeader(name = "Accept-Language", required = false) Locale loc,
            @Param("modifiedBy") String modifiedBy) {

        log.info("Start of Patch request");
    
        Optional<Country> optionalCountry = countryrepo.findById(id);
        if (optionalCountry.isPresent()) {

            Country cntry = optionalCountry.get();
            cntry.setModifiedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            cntry.setModifiedBy(modifiedBy);

            objectMap.forEach((key, value) -> {

                Field field = ReflectionUtils.findRequiredField(Country.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, cntry, value);

            });

            response.setErrorCode(null);
            response.setErrorDesc(null);
            response.setData(countryrepo.save(cntry));

            log.debug("Updated Country present at "+id + " id by "+ modifiedBy);
            log.info("End of Patch request");
            return new ResponseEntity<Object>(response, HttpStatus.OK);

        } else {

            String messageCode = messageSource.getMessage("ERROR_ID01", null, LocaleContextHolder.getLocale());
            String descMessage = messageSource.getMessage("ERROR_DESC01", null, LocaleContextHolder.getLocale());
            response.setErrorCode(messageCode);
            response.setErrorDesc(descMessage +"-- "+id);
            log.error(descMessage);
            response.setData(null);
            log.info("End of Patch request");

            return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
        }

    }

}
