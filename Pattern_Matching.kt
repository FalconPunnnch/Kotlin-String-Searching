import java.util.Scanner

// Fuerza Bruta
// Indica posición(n) de primer caracter de patrón(palabra/oración) a buscar.
fun buscarPatron(textoFB: String, patron: String): List<Int>{
    val posiciones = mutableListOf<Int>()

    val n = textoFB.length
    val m = patron.length

    for (i in 0..n - m){
        var j = 0
        while (j < m && textoFB[i + j] == patron[j]){
            j++
        }
        if (j == m){
            posiciones.add(i)
        }
    }
    return posiciones
}






//Boyer-Moore
//Función para crear la tabla de saltos de caracteres mal emparejados
private fun createBadCharacterTable(pattern: String): IntArray {
    val table = IntArray(256) { pattern.length } // Inicializamos todos los caracteres con la longitud del patrón

    for (i in 0 until pattern.length - 1) {
        val c = pattern[i]
        table[c.code] = pattern.length - 1 - i
    }

    return table
}

// Función para crear la tabla de saltos de sufijos
private fun createGoodSuffixTable(pattern: String): IntArray{
    val table = IntArray(pattern.length)
    val suffixes = IntArray(pattern.length)

    // Paso 1: Calcular los sufijos más largos que coinciden con sufijos más cortos
    var lastPrefixIndex = pattern.length
    for(i in pattern.length - 1 downTo 0){
        if(isPrefix(pattern, i + 1)) {
            lastPrefixIndex = i + 1
        }
        suffixes[i] = lastPrefixIndex
    }

    // Paso 2: Calcular los sufijos coincidentes más largos en el patrón
    for(i in 0 until pattern.length - 1){
        val suffixLength = getSuffixLength(pattern, i)
        table[suffixLength] = pattern.length - 1 - i + suffixLength
    }

    // Paso 3: Rellenar las posiciones restantes en la tabla con desplazamientos de sufijos
    for(i in 0 until pattern.length - 1){
        val suffixLength = getSuffixLength(pattern, i)
        if(suffixLength == i + 1){
            for(j in 0 until pattern.length - 1 - suffixLength + 1){
                if(table[j] == pattern.length){
                    table[j] = pattern.length - 1 - suffixLength + 1
                }
            }
        }
    }
    return table
}

// Función auxiliar para verificar si un substring es un prefijo del patrón
private fun isPrefix(pattern: String, p: Int): Boolean{
    for(i in p until pattern.length){
        if(pattern[i] != pattern[i - p]){
            return false
        }
    }
    return true
}

// Función auxiliar para obtener la longitud del sufijo coincidente más largo
private fun getSuffixLength(pattern: String, p: Int): Int{
    var length = 0
    var i = p
    var j = pattern.length - 1

    while(i >= 0 && pattern[i] == pattern[j]){
        length += 1
        i -= 1
        j -= 1
    }

    return length
}

// Función principal para realizar la búsqueda de la cadena objetivo en el patrón utilizando Boyer-Moore
fun boyerMooreSearch(pattern: String, text: String): List<Int>{
    val indices = mutableListOf<Int>()
    val badCharacterTable = createBadCharacterTable(pattern)
    val goodSuffixTable = createGoodSuffixTable(pattern)
    var i = 0

    while(i <= text.length - pattern.length){
        var j = pattern.length - 1

        while(j >= 0 && pattern[j] == text[i + j]){
            j -= 1
        }

        if(j < 0){
            indices.add(i)
            i += goodSuffixTable[0]
        }else{
            val charShift = badCharacterTable[text[i + j].code] - (pattern.length - 1 - j)
            val suffixShift = goodSuffixTable[j]
            i += maxOf(charShift, suffixShift)
        }
    }
    return indices
}






//Knuth-Morris-Pratt(KMP)
fun construirTablaLPS(patronKMP: String): IntArray{
    val m = patronKMP.length
    val lps = IntArray(m)
    var longitudPrefijo = 0
    var i = 1

    while(i < m){
        if(patronKMP[i] == patronKMP[longitudPrefijo]){
            longitudPrefijo++
            lps[i] = longitudPrefijo
            i++
        }else{
            if(longitudPrefijo != 0){
                longitudPrefijo = lps[longitudPrefijo - 1]
            }else{
                lps[i] = 0
                i++
            }
        }
    }
    return lps
}

fun buscarKMP(texto: String, patronKMP: String){
    val n = texto.length
    val m = patronKMP.length
    val lps = construirTablaLPS(patronKMP)
    var i = 0
    var j = 0

    while (i < n){
        if(texto[i] == patronKMP[j]){
            i++
            j++

            if(j == m){
                println("Se encontró una coincidencia en el índice ${i - j}")
                j = lps[j - 1]
            }
        }else{
            if(j != 0){
                j = lps[j - 1]
            }else{
                i++
            }
        }
    }
}




//Implementación Simple
fun main(){
    //Fuerza Bruta
    val scanner = Scanner(System.`in`)
    val textoFB = "Hola, esto es un ejemplo de búsqueda de patrón"

    print("Ingrese el patrón a buscar: ")
    val patron = scanner.nextLine()
    
    val posiciones = buscarPatron(textoFB, patron)

    if(posiciones.isNotEmpty()){
        println("El patrón se encontró en las siguientes posiciones:")
        posiciones.forEach {println(it)}
    }else{
        println("El patrón no se encontró en el texto.")
    }

    //Boyer-Moore
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    val pattern = "ipsum"

    val indices = boyerMooreSearch(pattern, text)
    if(indices.isNotEmpty()){
        println("El patrón \"$pattern\" se encontró en las posiciones: $indices")
    }else{
        println("El patrón \"$pattern\" no se encontró en el texto.")
    }

    //KMP
    val texto = "Lorem ipsum dolor sit amet"
    val patronKMP = "ipsum"

    buscarKMP(texto, patronKMP)
}
