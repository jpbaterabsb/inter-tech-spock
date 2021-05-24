import spock.lang.Specification

class LabelPrinter {
    def _(def message) {
        println message
        true
    }
}

Specification.mixin LabelPrinter
