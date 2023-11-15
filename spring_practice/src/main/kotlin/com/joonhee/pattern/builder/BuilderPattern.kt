package com.joonhee.pattern.builder

class Account (
    val name: String,
    val age: Int,
    val address: String

) {
    companion object {
        fun builder(name: String): Builder {
            return Builder(name = name)
        }
    }

    class Builder(val name: String) {
        private var builderAge: Int = 0
        private var builderAddress: String = ""

        fun age(age: Int): Builder {
            this.builderAge = age
            return this
        }

        fun address(address: String): Builder {
            this.builderAddress = address
            return this
        }

        fun build() :Account{
            return Account(
                name = this.name,
                age = builderAge,
                address = builderAddress
            )
        }
    }
}

/**
 * 자바에서는 생성자로 객체를 생성할 때 파라미터의 순서대로 값을 넣어줘야 하지만
 * 생성하기 위한 파라미터가 많아질 수록 순서에 따라 값을 넣기 어렵기 때문에 빌더패턴이라는 것을 사용하여 객체를 생성할 수 있도록 한다.
 * 하지만 코틀린에서는 생성자에 값을 넣을 때 파라미터의 이름을 명시할 수 있기 때문에 빌더패턴을 사용하지 않아도 된다.
 */

class AccountWithoutBuilder (
    val name: String,
    val age: Int = 0,
    val address: String = ""
)


fun main() {
    val account = Account.builder(name = "joonhee")
        .address("seoul")
        .build()
    println(account.age)

    val accountWithoutBuilder = AccountWithoutBuilder(
        name = "joonhee",
        address = "seoul"

    )

}
