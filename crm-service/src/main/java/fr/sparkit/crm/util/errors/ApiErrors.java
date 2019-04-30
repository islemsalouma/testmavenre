package fr.sparkit.crm.util.errors;

/**
 * Custom API exceptions errors status codes, should be unique for accounting
 * module. Any error code defined should be 20k > ERROR_CODE < 21k.
 *
 * @author amine
 */

public class ApiErrors {

    /*
     * TODO : add custom error codes depending on exception
     *
     */
    public static class Accounting {

        public final static int ENTITY_NOT_FOUND = 20004;
        public final static int LABEL_MIN_LENGTH = 20003;
        public final static int PARENT_ACCOUNT_DONT_EXISTS = 20000;
        public final static int ACCOUNT_EXISTING_CODE = 20001;
        public final static int ACCOUNT_EXISTING_LABEL = 20002;
        public final static int DOCUMENT_ACCOUNT_AMOUNT_CODE = 20100;
        public final static int DOCUMENT_ACCOUNT_WITHOUT_LINES_CODE = 20101;
        public final static int ACCOUNT_CODE_EXISTS = 20300;
        public final static int ACCOUNT_CREDIT_DEBIT_IS_DIFFERENT = 20301;
        public final static int ACCOUNT_CODE_DIFFERENT_THAN_PARENT = 20302;
        public final static int ACCOUNT_NEGATIVE_CREDIT_OR_DEBIT = 20303;
        public final static int JOURNAL_CODE_EXISTS = 20400;
        public final static int CHART_EXISTING_CODE_AND_LABEL = 20500;
        public static final int FISCAL_YEAR_INEXISTANT_FISCAL_YEAR = 20501;
        public static final int FISCAL_YEAR_MISSING_PARAMETERS = 20502;
        public static final int FISCAL_YEAR_DATES_OVERLAP_ERROR = 20503;
        public static final int FISCAL_YEAR_DATES_ORDER_INVALID = 20504;

    }

}