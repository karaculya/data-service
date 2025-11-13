package dnd.helper.dataservice.controller;

import openapi.api.PersonsApi;
import openapi.dto.ModifyPerson;
import openapi.dto.Person;
import dnd.helper.dataservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonsApi {
    private final PersonService service;

    @Override
    public ResponseEntity<Person> createPerson(Person person) {
        return ResponseEntity.ok(service.createPerson(person));
    }

    @Override
    public ResponseEntity<Void> deletePerson(Long id) {
        service.deletePerson(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Person>> getGamePersons(Long personId) {
        return null;
    }

    @Override
    public ResponseEntity<Person> getPerson(Long id) {
        return ResponseEntity.ok(service.getPerson(id));
    }

    @Override
    public ResponseEntity<Person> updatePerson(Long id, ModifyPerson modifyPerson) {
        return ResponseEntity.ok(service.updatePerson(id, modifyPerson));
    }
}
