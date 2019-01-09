package br.com.pemaza.supervisao.utilities

/**
 *Links de APIs
 */
const val PmzApiUrl = "http://192.168.150.116/questionario_supervisao/app/"
//const val PmzApiUrl = "http://www.mocky.io/"

/**
 * Chaves para acesso ao login na shared preference
 */
const val SharedPrefName = "br.com.pemaza.supervisao"
const val TokenKey = "UserToken"
const val UsuarioKey = "LoggedUsername"
const val UsuarioCodKey = "UserCod"
const val UsuarioFilialKey = "UserFilial"

/**
 * Nome do banco de dados
 */
const val DbName = "PmzSupervisaoDatabase"

/**
 * Extras usadas em intents
 */
const val UnauthorizedExtra = "UnauthorizedExtra"
const val QuestionarioSecoesExtra = "QuestionarioSecoesExtra"
const val QuestionarioExtra = "QuestionarioExtra"
const val AnotacaoExtra = "AnotacaoExtra"

/**
 * Tags para identificação de fragments
 */
const val NovaAnotacaoFragment = "NovaAnotacaoFragment"