<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="p-5 text-center mt-3 mb-3">
        <div class="p-5 text-center">
            <h1 class="mb-3"><@spring.message "order"/></h1>
            <div class="mt-5 border">
                <a href="/create-order/services"
                   class="btn btn-success mt-2 shadow-none"><@spring.message "select.service"/></a>
                <#if appointment.service??>
                    <p class="mt-3">${appointment.service.title}</p>
                    <form action="/create-order/services/delete" method="post">
                        <button class="btn btn-warning shadow-none"><@spring.message "remove.service"/></button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </#if>
                <div class="dropdown-divider"></div>

                <a href="/create-order/masters"
                   class="btn btn-success mt-1 shadow-none"><@spring.message "select.master"/></a>
                <#if appointment.master??>
                    <p class="mt-3">${appointment.master.userInfo.name} ${appointment.master.userInfo.surname}</p>
                    <form action="/create-order/masters/delete" method="post">
                        <button class="btn btn-warning shadow-none"><@spring.message "remove.master"/></button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </#if>

                <div class="dropdown-divider"></div>

                <a href="/create-order/timeslots"
                   class="btn btn-success mb-2 shadow-none"><@spring.message "select.timeslot"/></a>
                <#if appointment.dateTime??>
                    <p class="mt-3">${appointment.dateTime.toString()?replace("T", " ")}</p>
                    <form action="/create-order/timeslots/delete" method="post">
                        <button class="btn btn-warning mb-2 shadow-none"><@spring.message "remove.timeslot"/></button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </#if>

            </div>
            <#if appointment.dateTime?? &&
            appointment.master?? &&
            appointment.service??>
                <div class="mx-auto w-25">
                    <form action="/create-order/submit" method="POST" class="mt-3">
                        <div class="mb-3">
                            <label for="ccnumber" class="form-label"><@spring.message "credit.card.number"/></label>
                            <input type="text" class="form-control ${(CCNumberError??)?string('is-invalid', '')}"
                                   id="ccnumber" name="CCNumber" value="<#if creditCard??>${creditCard.CCNumber}</#if>">
                            <#if CCNumberError??>
                                <div class="invalid-feedback">
                                    ${CCNumberError}
                                </div>
                            </#if>
                        </div>
                        <div class="mb-3">
                            <label for="ownerName" class="form-label"><@spring.message "owner.name"/></label>
                            <input type="text" class="form-control ${(ownerNameError??)?string('is-invalid', '')}"
                                   id="ownerName" name="ownerName" value="<#if creditCard??>${creditCard.ownerName}</#if>">
                            <#if ownerNameError??>
                                <div class="invalid-feedback">
                                    ${ownerNameError}
                                </div>
                            </#if>
                        </div>
                            <div class="mb-3">
                                <label for="cc-expiration"><@spring.message "expiration.date"/></label>
                                <input type="text" class="form-control ${(expirationDateError??)?string('is-invalid', '')}"
                                       id="cc-expiration" name="expirationDate" value="<#if creditCard??>${creditCard.expirationDate}</#if>">
                            </div>
                            <#if expirationDateError??>
                                <div class="invalid-feedback">
                                    ${expirationDateError}
                                </div>
                            </#if>
                            <div class="mb-3">
                                <label for="cc-cvv">CVV</label>
                                <input type="password" class="form-control ${(CVVError??)?string('is-invalid', '')}"
                                       id="cc-cvv" maxlength="3" name="CVV">
                            </div>
                            <#if CVVError??>
                                <div class="invalid-feedback">
                                    ${CVVError}
                                </div>
                            </#if>
                        <button class="btn btn-primary shadow-none"><@spring.message "submit"/></button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </#if>
        </div>
    </div>
</@c.page>