fun resolverSudoku(sudoku: IntArray, pos: Int): Boolean {
    // Caso base: se llenaron todas las casillas
    if (pos == 81) {
        return true
    }
    if (sudoku[pos] != 0) {
        return resolverSudoku(sudoku, pos+1)
    }
    for (num in 1..9) {
        if (esValido(sudoku, num, pos)) {
            sudoku[pos] = num
            if (resolverSudoku(sudoku, pos+1)) {
                return true
            }
            // Backtrack: se devuelve el cambio para seguir probando soluciones
            sudoku[pos] = 0
        }        
    }
    return false
}

fun esValido(sudoku: IntArray, numero: Int, pos: Int): Boolean {
    val fila = pos / 9
    val col = pos - fila * 9

    // Verificar si ya se encuentra el numero en la fila
    for (i in 9 * fila until 9 * (fila + 1)) {
        if (sudoku[i] == numero && i != pos) return false
    }
    // Verificar si ya se encuentra el numero en la columna
    for (j in 0 + col until 81 step 9) {
        if (sudoku[j] == numero && j != pos) return false
    }

    val filaInicio = (fila / 3) * 3
    val colInicio = (col / 3) * 3
    val inicioSubtablero = filaInicio * 9 + colInicio

    // Verificar si ya se encuentra el numero en el subtablero
    for (k in inicioSubtablero until inicioSubtablero+3) {
        if (sudoku[k] == numero && k != pos) return false
    }
    for (k in inicioSubtablero+9 until inicioSubtablero+12) {
        if (sudoku[k] == numero && k != pos) return false
    }
    for (k in inicioSubtablero+18 until inicioSubtablero+21) {
        if (sudoku[k] == numero && k != pos) return false
    }
    return true
}

fun main(args: Array<String>) {
    if (args.isEmpty() || args[0].length != 81) {
        println("NOSOLUTION")
    }

    val entrada = args[0]
    val tablero = IntArray(81) {i -> entrada[i] - '0'}
    val solucion = resolverSudoku(tablero, 0)

    if (solucion) {
        println(tablero.joinToString(""))
    } else {println("NOSOLUTION")}
}