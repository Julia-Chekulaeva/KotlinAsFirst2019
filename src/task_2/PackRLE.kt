val regex = Regex("""pack-rle (-z|-u) (-out [а-яА-Я\w/]+\.txt )?[а-яА-Я\w/]+\.txt""")

class PackRLE(str: String) {

    init {
        if (!str.matches(regex)) throw IllegalArgumentException("Утилита введена неправильно")
    }

    private val elems = str.split(" ")

    val inputName = elems.last()

    val outputName = if (elems.size == 5) elems[3] else elems[2].removeSuffix("txt") + "rle"

    val toRLE = elems[1] == "-z"

}