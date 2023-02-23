package com.dtzhejiang.irs.res.bill.infra.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SubReportExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSubTypeIsNull() {
            addCriterion("sub_type is null");
            return (Criteria) this;
        }

        public Criteria andSubTypeIsNotNull() {
            addCriterion("sub_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubTypeEqualTo(Long value) {
            addCriterion("sub_type =", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotEqualTo(Long value) {
            addCriterion("sub_type <>", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeGreaterThan(Long value) {
            addCriterion("sub_type >", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("sub_type >=", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeLessThan(Long value) {
            addCriterion("sub_type <", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeLessThanOrEqualTo(Long value) {
            addCriterion("sub_type <=", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeIn(List<Long> values) {
            addCriterion("sub_type in", values, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotIn(List<Long> values) {
            addCriterion("sub_type not in", values, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeBetween(Long value1, Long value2) {
            addCriterion("sub_type between", value1, value2, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotBetween(Long value1, Long value2) {
            addCriterion("sub_type not between", value1, value2, "subType");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesIsNull() {
            addCriterion("operation_indices is null");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesIsNotNull() {
            addCriterion("operation_indices is not null");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesEqualTo(String value) {
            addCriterion("operation_indices =", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesNotEqualTo(String value) {
            addCriterion("operation_indices <>", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesGreaterThan(String value) {
            addCriterion("operation_indices >", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesGreaterThanOrEqualTo(String value) {
            addCriterion("operation_indices >=", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesLessThan(String value) {
            addCriterion("operation_indices <", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesLessThanOrEqualTo(String value) {
            addCriterion("operation_indices <=", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesLike(String value) {
            addCriterion("operation_indices like", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesNotLike(String value) {
            addCriterion("operation_indices not like", value, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesIn(List<String> values) {
            addCriterion("operation_indices in", values, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesNotIn(List<String> values) {
            addCriterion("operation_indices not in", values, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesBetween(String value1, String value2) {
            addCriterion("operation_indices between", value1, value2, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationIndicesNotBetween(String value1, String value2) {
            addCriterion("operation_indices not between", value1, value2, "operationIndices");
            return (Criteria) this;
        }

        public Criteria andOperationDataIsNull() {
            addCriterion("operation_data is null");
            return (Criteria) this;
        }

        public Criteria andOperationDataIsNotNull() {
            addCriterion("operation_data is not null");
            return (Criteria) this;
        }

        public Criteria andOperationDataEqualTo(String value) {
            addCriterion("operation_data =", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataNotEqualTo(String value) {
            addCriterion("operation_data <>", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataGreaterThan(String value) {
            addCriterion("operation_data >", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataGreaterThanOrEqualTo(String value) {
            addCriterion("operation_data >=", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataLessThan(String value) {
            addCriterion("operation_data <", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataLessThanOrEqualTo(String value) {
            addCriterion("operation_data <=", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataLike(String value) {
            addCriterion("operation_data like", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataNotLike(String value) {
            addCriterion("operation_data not like", value, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataIn(List<String> values) {
            addCriterion("operation_data in", values, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataNotIn(List<String> values) {
            addCriterion("operation_data not in", values, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataBetween(String value1, String value2) {
            addCriterion("operation_data between", value1, value2, "operationData");
            return (Criteria) this;
        }

        public Criteria andOperationDataNotBetween(String value1, String value2) {
            addCriterion("operation_data not between", value1, value2, "operationData");
            return (Criteria) this;
        }

        public Criteria andNormalValueIsNull() {
            addCriterion("normal_value is null");
            return (Criteria) this;
        }

        public Criteria andNormalValueIsNotNull() {
            addCriterion("normal_value is not null");
            return (Criteria) this;
        }

        public Criteria andNormalValueEqualTo(String value) {
            addCriterion("normal_value =", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueNotEqualTo(String value) {
            addCriterion("normal_value <>", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueGreaterThan(String value) {
            addCriterion("normal_value >", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueGreaterThanOrEqualTo(String value) {
            addCriterion("normal_value >=", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueLessThan(String value) {
            addCriterion("normal_value <", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueLessThanOrEqualTo(String value) {
            addCriterion("normal_value <=", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueLike(String value) {
            addCriterion("normal_value like", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueNotLike(String value) {
            addCriterion("normal_value not like", value, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueIn(List<String> values) {
            addCriterion("normal_value in", values, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueNotIn(List<String> values) {
            addCriterion("normal_value not in", values, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueBetween(String value1, String value2) {
            addCriterion("normal_value between", value1, value2, "normalValue");
            return (Criteria) this;
        }

        public Criteria andNormalValueNotBetween(String value1, String value2) {
            addCriterion("normal_value not between", value1, value2, "normalValue");
            return (Criteria) this;
        }

        public Criteria andOperationResultsIsNull() {
            addCriterion("operation_results is null");
            return (Criteria) this;
        }

        public Criteria andOperationResultsIsNotNull() {
            addCriterion("operation_results is not null");
            return (Criteria) this;
        }

        public Criteria andOperationResultsEqualTo(String value) {
            addCriterion("operation_results =", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsNotEqualTo(String value) {
            addCriterion("operation_results <>", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsGreaterThan(String value) {
            addCriterion("operation_results >", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsGreaterThanOrEqualTo(String value) {
            addCriterion("operation_results >=", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsLessThan(String value) {
            addCriterion("operation_results <", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsLessThanOrEqualTo(String value) {
            addCriterion("operation_results <=", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsLike(String value) {
            addCriterion("operation_results like", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsNotLike(String value) {
            addCriterion("operation_results not like", value, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsIn(List<String> values) {
            addCriterion("operation_results in", values, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsNotIn(List<String> values) {
            addCriterion("operation_results not in", values, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsBetween(String value1, String value2) {
            addCriterion("operation_results between", value1, value2, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsNotBetween(String value1, String value2) {
            addCriterion("operation_results not between", value1, value2, "operationResults");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusIsNull() {
            addCriterion("operation_results_status is null");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusIsNotNull() {
            addCriterion("operation_results_status is not null");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusEqualTo(String value) {
            addCriterion("operation_results_status =", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusNotEqualTo(String value) {
            addCriterion("operation_results_status <>", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusGreaterThan(String value) {
            addCriterion("operation_results_status >", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusGreaterThanOrEqualTo(String value) {
            addCriterion("operation_results_status >=", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusLessThan(String value) {
            addCriterion("operation_results_status <", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusLessThanOrEqualTo(String value) {
            addCriterion("operation_results_status <=", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusLike(String value) {
            addCriterion("operation_results_status like", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusNotLike(String value) {
            addCriterion("operation_results_status not like", value, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusIn(List<String> values) {
            addCriterion("operation_results_status in", values, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusNotIn(List<String> values) {
            addCriterion("operation_results_status not in", values, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusBetween(String value1, String value2) {
            addCriterion("operation_results_status between", value1, value2, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andOperationResultsStatusNotBetween(String value1, String value2) {
            addCriterion("operation_results_status not between", value1, value2, "operationResultsStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusIsNull() {
            addCriterion("sub_status is null");
            return (Criteria) this;
        }

        public Criteria andSubStatusIsNotNull() {
            addCriterion("sub_status is not null");
            return (Criteria) this;
        }

        public Criteria andSubStatusEqualTo(String value) {
            addCriterion("sub_status =", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusNotEqualTo(String value) {
            addCriterion("sub_status <>", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusGreaterThan(String value) {
            addCriterion("sub_status >", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusGreaterThanOrEqualTo(String value) {
            addCriterion("sub_status >=", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusLessThan(String value) {
            addCriterion("sub_status <", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusLessThanOrEqualTo(String value) {
            addCriterion("sub_status <=", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusLike(String value) {
            addCriterion("sub_status like", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusNotLike(String value) {
            addCriterion("sub_status not like", value, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusIn(List<String> values) {
            addCriterion("sub_status in", values, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusNotIn(List<String> values) {
            addCriterion("sub_status not in", values, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusBetween(String value1, String value2) {
            addCriterion("sub_status between", value1, value2, "subStatus");
            return (Criteria) this;
        }

        public Criteria andSubStatusNotBetween(String value1, String value2) {
            addCriterion("sub_status not between", value1, value2, "subStatus");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andApprovalIdIsNull() {
            addCriterion("approval_id is null");
            return (Criteria) this;
        }

        public Criteria andApprovalIdIsNotNull() {
            addCriterion("approval_id is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalIdEqualTo(String value) {
            addCriterion("approval_id =", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdNotEqualTo(String value) {
            addCriterion("approval_id <>", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdGreaterThan(String value) {
            addCriterion("approval_id >", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdGreaterThanOrEqualTo(String value) {
            addCriterion("approval_id >=", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdLessThan(String value) {
            addCriterion("approval_id <", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdLessThanOrEqualTo(String value) {
            addCriterion("approval_id <=", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdLike(String value) {
            addCriterion("approval_id like", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdNotLike(String value) {
            addCriterion("approval_id not like", value, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdIn(List<String> values) {
            addCriterion("approval_id in", values, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdNotIn(List<String> values) {
            addCriterion("approval_id not in", values, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdBetween(String value1, String value2) {
            addCriterion("approval_id between", value1, value2, "approvalId");
            return (Criteria) this;
        }

        public Criteria andApprovalIdNotBetween(String value1, String value2) {
            addCriterion("approval_id not between", value1, value2, "approvalId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}