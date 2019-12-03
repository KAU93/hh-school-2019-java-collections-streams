package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {

      return personAreaIds.entrySet().stream()
              .map( el -> {
                  String name = persons.stream()    // Получаем имя персоны по id
                          .filter( f-> f.getId() == el.getKey())
                          .map(namePerson->namePerson.getFirstName())
                          .findAny().get();
                  Set<String> areasName = el.getValue().stream()  // Получаем множество названий регионов для конкретной персоны
                          .map(id -> {
                              return areas.stream()
                                      .filter( ar->ar.getId() == id)
                                      .map( m->m.getName())
                                      .findAny().get();
                          })
                          .collect(Collectors.toSet());
                  return areasName.stream().map(areaName->{     // Формируем выходную строку Имя - Регион и формируем в Set
                      return name + " - " + areaName;
                  })
                          .collect(Collectors.toSet());
              })
              .reduce((s1,s2) ->{       // Объединяем получившиеся множества
                  Set<String> rez = s1;
                  rez.addAll(s2);
                  return rez;
              }).orElse(new HashSet<String>());
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
