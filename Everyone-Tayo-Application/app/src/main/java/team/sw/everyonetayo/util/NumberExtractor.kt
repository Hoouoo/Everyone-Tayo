package team.sw.everyonetayo.util

class NumberExtractor {

    companion object{
        fun numberExtraction(input: String): String {
            return input.replace("[^0-9]".toRegex(), "");
        }
    }
}