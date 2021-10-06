package com.jun.service.account.domain.utils;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class MongoExceptionUtils {

  @Transactional(propagation = Propagation.MANDATORY)
  public void processException(RuntimeException ex) {
    MongoException mongoException = null;
    if (ex.getCause() instanceof MongoException) {
      mongoException = (MongoException) ex.getCause();
    } else if (ex.getCause() instanceof MongoCommandException) {
      mongoException = (MongoCommandException) ex.getCause();
    }
    if (mongoException != null
        && mongoException.hasErrorLabel(MongoException.TRANSIENT_TRANSACTION_ERROR_LABEL)) {
      log.debug("TransientTransactionError aborting transaction and retrying ...");
      throw mongoException;
    } else if (mongoException != null
        && mongoException.hasErrorLabel(MongoException.UNKNOWN_TRANSACTION_COMMIT_RESULT_LABEL)) {
      log.debug("UnknownTransactionCommitResult, retrying commit operation ...");
      throw mongoException;
    }
    throw ex;
  }
}
