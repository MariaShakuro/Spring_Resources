import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Send driver event to Kafka topic"

    input {
        triggeredBy('sendDriverEvent("123")')
    }

    outputMessage {
        sentTo 'driver-events'
        body("123")
        headers {
            header('contentType', applicationJson())
        }
    }
}
