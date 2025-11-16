package dnd.helper.dataservice.service;

import dnd.helper.dataservice.exception.NotFoundException;
import dnd.helper.dataservice.model.entity.InventoryItemEntity;
import dnd.helper.dataservice.model.entity.PersonEntity;
import dnd.helper.dataservice.model.entity.StatEntity;
import dnd.helper.dataservice.model.mapper.InventoryItemMapper;
import dnd.helper.dataservice.model.mapper.PersonMapper;
import dnd.helper.dataservice.model.mapper.StatMapper;
import dnd.helper.dataservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import openapi.dto.ModifyPerson;
import openapi.dto.Person;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final PersonMapper mapper;
    private final InventoryItemMapper inventoryMapper;
    private final StatMapper statMapper;

    public Person createPerson(Person person) {
        PersonEntity entity = new PersonEntity();
        Long id = entity.getId();
        entity = mapper.toEntity(person);
        entity.setId(id);
        PersonEntity finalEntity = entity;
        entity.setInventory(person.getInventory().stream()
                .map(inventoryItem -> {
                    InventoryItemEntity item = new InventoryItemEntity();
                    Long inventoryId = item.getId();
                    item = inventoryMapper.toEntity(inventoryItem);
                    item.setId(inventoryId);
                    finalEntity.addInventoryItem(item);
                    return item;
                }).toList());
        entity.setStats(person.getStats().stream()
                .map(stat -> {
                    StatEntity item = new StatEntity();
                    Long inventoryId = item.getId();
                    item = statMapper.toEntity(stat);
                    item.setId(inventoryId);
                    finalEntity.addStatItem(item);
                    return item;
                }).toList());
        return mapper.toDto(repository.save(finalEntity));
    }
    public void deletePerson(Long personId) {
        if (findPersonById(personId) != null) repository.deleteById(personId);
    }

    public Person getPerson(Long personId) {
        return mapper.toDto(findPersonById(personId));
    }

    public Person updatePerson(Long personId, ModifyPerson modifyPerson) {
        PersonEntity entity = findPersonById(personId);

        if (modifyPerson.getBio() != null)
            entity.setBio(modifyPerson.getBio());
        if (modifyPerson.getName() != null)
            entity.setName(modifyPerson.getName());
        if (modifyPerson.getInventory() != null)
            entity.setInventory(modifyPerson.getInventory()
                    .stream()
                    .map(inventoryMapper::toEntity)
                    .toList());
        if (modifyPerson.getLevel() != null)
            entity.setLevel(modifyPerson.getLevel());
        if (modifyPerson.getStats() != null)
            entity.setStats(modifyPerson.getStats()
                    .stream()
                    .map(statMapper::toEntity)
                    .toList());

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    PersonEntity findPersonById(Long personId) {
        return repository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Персонаж с id " + personId + " не найден"));
    }
}
