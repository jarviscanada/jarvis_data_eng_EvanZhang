import scala.io.Source

/**
 * Count number of elements
 * Get the first element
 * Get the last element
 * Get the first 5 elements
 * Get the last 5 elements
 *
 * hint: use the following methods
 * head
 * last
 * size
 * take
 * tails
 */
val ls = List.range(0,10)
//write you solution here
val ls_count = ls.size
val ls_first = ls.head
val ls_last = ls.tail
val ls_first5 = ls.take(5)
val ls_last5 = ls.takeRight(5)

/**
 * Double each number from the numList and return a flatten list
 * e.g.res4: List[Int] = List(2, 3, 4)
 *
 * Compare flatMap VS ls.map().flatten
 */
val numList = List(List(1,2), List(3))
//write you solution here
numList.flatMap(x => x.map(_ * 2))
//shorthand for:
//numList.map(x => x.map(_ * 2)).flatten

/**
 * Sum List.range(1,11) in three ways
 * hint: sum, reduce, foldLeft
 *
 * Compare reduce and foldLeft
 * https://stackoverflow.com/questions/7764197/difference-between-foldleft-and-reduceleft-in-scala
 */
//write you solution here
val ls = List.range(1,11)
val ls_sum = ls.sum
val ls_reduce = ls.reduce((m: Int, n: Int) => m + n)
//foldLeft allows you to specify which starting index
val ls_foldLeft = ls.foldLeft(0)((m: Int, n: Int) => m + n)

/**
 * Practice Map and Optional
 *
 * Map question1:
 *
 * Compare get vs getOrElse (Scala Optional)
 * countryMap.get("Amy");
 * countryMap.getOrElse("Frank", "n/a");
 */
val countryMap = Map("Amy" -> "Canada", "Sam" -> "US", "Bob" -> "Canada")
//if key is in map, return Some(value), requires unwrapping Option[]
countryMap.get("Amy")
//if key is not in map, return None
countryMap.get("edward")
//if key is not in map, return "n/a", else option
countryMap.getOrElse("edward", "n/a")
//automatically unwraps value
countryMap.getOrElse("Amy", "n/a")

/**
 * Map question2:
 *
 * create a list of (name, country) tuples using `countryMap` and `names`
 * e.g. res2: List[(String, String)] = List((Amy,Canada), (Sam,US), (Eric,n/a), (Amy,Canada))
 */
val names = List("Amy", "Sam", "Eric", "Amy")
//write you solution here
val names_countries = names.map(name => (name, countryMap.getOrElse(name, "n/a")))

/**
 * Map question3:
 *
 * count number of people by country. Use `n/a` if the name is not in the countryMap  using `countryMap` and `names`
 * e.g. res0: scala.collection.immutable.Map[String,Int] = Map(Canada -> 2, n/a -> 1, US -> 1)
 * hint: map(get_value_from_map) ; groupBy country; map to (country,count)
 */
//write you solution here
names_countries.groupBy(_._2).mapValues(_.size)

/**
 * number each name in the list from 1 to n
 * e.g. res3: List[(Int, String)] = List((1,Amy), (2,Bob), (3,Chris))
 */
val names2 = List("Amy", "Bob", "Chris", "Dann")
//write you solution here
names2.map(name => (names2.indexOf(name) + 1, name))

/**
 * SQL questions1:
 *
 * read file lines into a list
 * lines: List[String] = List(id,name,city, 1,amy,toronto, 2,bob,calgary, 3,chris,toronto, 4,dann,montreal)
 */
//write you solution here
val file = Source.fromFile("/home/centos/dev/jarvis_data_eng_EvanZhang/spark/src/main/resources/employees.csv")
val file_lines = file.getLines.toList
file.close()

/**
 * SQL questions2:
 *
 * Convert lines to a list of employees
 * e.g. employees: List[Employee] = List(Employee(1,amy,toronto), Employee(2,bob,calgary), Employee(3,chris,toronto), Employee(4,dann,montreal))
 */
//write you solution here
class Employee(in: String) {
  val split = in.split(",")
  var id: Int = split(0).toInt; var name: String = split(1); var city: String = split(2); var age: Int = split(3).toInt
  override def toString: String = s"Employee($id,$name,$city,$age)"
}
//val emp = new Employee("1,amy,toronto,20,")
val employee_list = file_lines.drop(1).map(in => new Employee(in))

/**
 * SQL questions3:
 *
 * Implement the following SQL logic using functional programming
 * SELECT uppercase(city)
 * FROM employees
 *
 * result:
 * upperCity: List[Employee] = List(Employee(1,amy,TORONTO,20), Employee(2,bob,CALGARY,19), Employee(3,chris,TORONTO,20), Employee(4,dann,MONTREAL,21), Employee(5,eric,TORONTO,22))
 */
//write you solution here
val upperCity = employee_list.map(employee => {employee.city = employee.city.toUpperCase(); employee})

/**
 * SQL questions4:
 *
 * Implement the following SQL logic using functional programming
 * SELECT uppercase(city)
 * FROM employees
 * WHERE city = 'toronto'
 *
 * result:
 * res5: List[Employee] = List(Employee(1,amy,TORONTO,20), Employee(3,chris,TORONTO,20), Employee(5,eric,TORONTO,22))
 */
//write you solution here
upperCity.filter(employee => employee.city == "TORONTO")

/**
 * SQL questions5:
 *
 * Implement the following SQL logic using functional programming
 *
 * SELECT uppercase(city), count(*)
 * FROM employees
 * GROUP BY city
 *
 * result:
 * cityNum: scala.collection.immutable.Map[String,Int] = Map(CALGARY -> 1, TORONTO -> 3, MONTREAL -> 1)
 */
//write you solution here
upperCity.groupBy(employee => employee.city).mapValues(cities => cities.size)

/**
 * SQL questions6:
 *
 * Implement the following SQL logic using functional programming
 *
 * SELECT uppercase(city), count(*)
 * FROM employees
 * GROUP BY city,age
 *
 * result:
 * res6: scala.collection.immutable.Map[(String, Int),Int] = Map((MONTREAL,21) -> 1, (CALGARY,19) -> 1, (TORONTO,20) -> 2, (TORONTO,22) -> 1)
 */
//groupBy as tuple
upperCity.groupBy(employee => (employee.city, employee.age)).mapValues(city_age => city_age.size)