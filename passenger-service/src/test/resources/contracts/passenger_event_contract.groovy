import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Send passenger event to Kafka topic"

    input {
        triggeredBy('sendPassengerEvent("123")')
    }

    outputMessage {
        sentTo 'passenger-events'
        body("123")
        headers {
            header('contentType', applicationJson())
        }
    }
}
