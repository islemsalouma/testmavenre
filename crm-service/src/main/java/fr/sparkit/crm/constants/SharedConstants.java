package fr.sparkit.crm.constants;

public final class SharedConstants {

    public static final class Accounting {
        public static final String EXISTING_CODE = "Existing code";
        public static final String EXISTING_CODE_AND_LABEL = "Existing code and label";
        public static final String EXISTING_LABEL = "Existing label";
        public static final String SUCCESS_OPERATION = "Success operation";
        public static final String PREFIX_ERROR = "Code should has prefix of his parent code";
        public static final String VALID_DOCUMENT = "Valid document";
        public static final String INVALID_DOCUMENT = "Invalid document";
        public static final String INVALID_TOTAL_AMOUNT_DOCUMENT = "Invalid document";
        public static final String CODE_DOCUMENT_EXIST = "Code document exist";
        public static final String INEXISTANT_ELEMENT = "The element doesn't exist";
        public static final String MISSING_PARAMETERS = "Missing parameters";
        public static final String DATES_OVERLAP_ERROR = "You can not have an overlap between two fiscal years, please correct the start and/or end dates of your fiscal year";
        public static final String DATES_ORDER_INVALID = "The ending date must not be prior to the starting date.";
        public static final int PAGE_DEFAULT_SIZE = 10;
        public static final int LABEL_MIN_LENGTH = 3;

        private Accounting() {
        }

    }

    private SharedConstants() {
    }

}
