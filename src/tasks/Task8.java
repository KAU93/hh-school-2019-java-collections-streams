package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    // В случае пустого стрима, Collectors.toList() вернет пустой список
    // По сравнению с начальным кодом, этот выглядит более понятным и лаконичным
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {

    return new HashSet<>(getNames(persons)); // Осознал, исправил
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getFirstName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Создадим Map<Integer, String> с помощью Collectors.toMap.
    // При возникновении коллизий выберем первый добавленный элемент
    return persons.stream().collect(Collectors.toMap(Person::getId,
                                                      this::convertPersonToString,
                                                      (s1,s2) -> s1));

  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
  Set<Person> setPerson2 = new HashSet<Person>(persons2);  // Улучшим метод contains до O(1)!!!
  return persons1.stream().anyMatch(setPerson2::contains); // Вместо equals надо использовать contains
  }

// В изначальном коде изменялась переменная count класса Task8.
// При дальнейшем развитии класса, она может быть использована в других методах,
// и ее изменение здесь приведет к ошибке, которую очень сложно отловить
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); // Метод count вернет кол-во элементов в стриме после фильтра
  }


  @Override
  public boolean check() {
//    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
