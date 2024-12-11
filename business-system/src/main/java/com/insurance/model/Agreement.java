package com.insurance.model;

import com.insurance.enums.AgreementStatus;

public record Agreement(String id, String customerId, AgreementStatus status)  {}
