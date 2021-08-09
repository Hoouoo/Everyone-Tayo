package team.sw.everyonetayo.domain

class WrappedString {
    var myString: String

    constructor(myString: String) {
        this.myString = myString
    }

    override fun toString(): String {
        return myString
    }
}