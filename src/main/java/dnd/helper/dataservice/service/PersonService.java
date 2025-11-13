package dnd.helper.dataservice.service;

import dnd.helper.dataservice.model.mapper.InventoryItemMapper;
import dnd.helper.dataservice.model.mapper.StatMapper;
import openapi.dto.ModifyPerson;
import openapi.dto.Person;
import dnd.helper.dataservice.exception.NotFoundException;
import dnd.helper.dataservice.model.PersonEntity;
import dnd.helper.dataservice.model.mapper.PersonMapper;
import dnd.helper.dataservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final PersonMapper mapper;
    private final InventoryItemMapper inventoryItemMapper;
    private final StatMapper statMapper;

    public Person createPerson(Person person) {
        PersonEntity entity = repository.save(mapper.toEntity(person));
        return mapper.toDto(entity);
    }
    public void deletePerson(Long personId) {
        if (findPersonById(personId) != null) repository.deleteById(personId);
    }

    public Person getPerson(Long personId) {
        return mapper.toDto(findPersonById(personId));
    }

    public Person updatePerson(Long personId, ModifyPerson modifyPerson) {
        PersonEntity entity = findPersonById(personId);
        if (modifyPerson.getBio() != null) entity.setBio(modifyPerson.getBio());
        if (modifyPerson.getName() != null) entity.setName(modifyPerson.getName());
        if (modifyPerson.getInventory() != null) entity.setInventory(modifyPerson.getInventory()
                .stream()
                .map(inventoryItemMapper::toEntity)
                .toList());
        if (modifyPerson.getLevel() != null) entity.setLevel(modifyPerson.getLevel());
        if (modifyPerson.getStats() != null) entity.setStats(modifyPerson.getStats()
                .stream()
                .map(statMapper::toEntity)
                .toList());

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    private PersonEntity findPersonById(Long personId) {
        return repository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Персонаж с id " + personId + " не найден"));
    }
}
