import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Get driver profile by ID"

    request {
        method GET()
        urlPath("/drivers/profile/1")
    }

    response {
        status 200
        body([
                id: 1,
                name: "John Doe",
                licenseNumber: "LICENSE123",
                rating: 5
        ])
        headers {
            contentType(applicationJson())
        }
    }
}
