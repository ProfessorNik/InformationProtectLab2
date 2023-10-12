import kotlin.math.pow


interface MyCipher {
    fun encrypt(message: String): String
    fun decrypt(encryptedMessage: String): String
}

class LCG(private val encryptionKey: Long) : MyCipher {
    private val modulus = 2.0.pow(32).toLong()
    private val multiplier = 1103515245L
    private val increment = 12345L

    override fun encrypt(message: String): String {
        var seed = encryptionKey
        return message.map { character ->
            seed = generateRandom(seed)
            (character.code xor seed.toInt()).toChar()
        }.joinToString("")
    }

    override fun decrypt(encryptedMessage: String) = encrypt(encryptedMessage)

    private fun generateRandom(seed: Long): Long = (multiplier * seed + increment) % modulus
}

fun test(cipher: MyCipher, message: String) {
    val encryptedMessage = cipher.encrypt(message)
    val decryptedMessage = cipher.decrypt(encryptedMessage)

    println("Original message: $message")
    println("Encrypted message: $encryptedMessage")
    println("Decrypted message: $decryptedMessage")
}


class PirsonHash {
    private val mod = 256
    private val table: List<Int> = List(mod) { i -> i }.shuffled()

    fun count(message: String) =
        message.fold((message.length) % mod) { hash, character ->
            table[(hash + character.code) % mod]
        }

}

fun test(pirsonHash: PirsonHash, message: String) {
    val hash = pirsonHash.count(message)

    println("Original message: $message")
    println("Hashing message: $hash")
}

fun main(args: Array<String>) {
    println("Влияние лавинного эффекта")
    val pirsonHash = PirsonHash()

    listOf("11111", "11112", "11113", "11114")
        .forEach { message -> test(pirsonHash, message) }
}

fun createLCGCipher(): MyCipher {
    val encryptionKey = 12345L
    return LCG(encryptionKey)
}


