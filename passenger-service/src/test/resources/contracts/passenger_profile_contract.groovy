import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Get passenger profile by email"

    request {
        method GET()
        urlPath("/api/passenger/alice@example.com")
    }

    response {
        status 200
        body([
                id: 1,
                name: "Alice",
                email: "alice@example.com",
                password: "password123",
                phoneNumber: "1234567890",
                promocode: "PROMO123"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}
