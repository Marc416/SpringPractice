package com.joonhee.pattern.builder

class Account (
    val name: String,
    val age: Int,
    val address: String

){
    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
    class Builder {
        private var builderName: String = ""
        private var builderAge: Int = 0
        private var builderAddress: String = ""
        fun name(name: String) :Builder{
            this.builderName = name
            return this
        }

        fun age(age: Int) :Builder{
            this.builderAge = age
            return this
        }

        fun address(address: String) :Builder{
            this.builderAddress = address
            return this
        }

        fun build() :Account{
            return Account(
                name = builderName,
                age = builderAge,
                address = builderAddress
            )
        }
    }
}

fun main() {
    val account = Account.builder()
        .name("joonhee")
        .address("seoul")
        .build()
    println(account.age)
}