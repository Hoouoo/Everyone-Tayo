package team.sw.everyonetayo.util

class NumberExtractor {

    companion object{
        fun numberExtraction(input: String): String {
            input.replace("다시", "-")
            return input.replace("[^0123456789-]".toRegex(), "");
            return input;
        }
    }
}