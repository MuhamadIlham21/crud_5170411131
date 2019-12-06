package com.app.id.crud_5170411131

class ApiEndPoint {

    companion object {
        private val SERVER = "http://10.0.2.2/universitas/"
        val getAll_fakultas = SERVER + "getAll_fakultas.php"
        val addFakultas = SERVER + "addFakultas.php"
        val updateFakultas = SERVER + "updateFakultas.php"
        val deleteFakultas = SERVER + "deleteFakultas.php"
    }
}