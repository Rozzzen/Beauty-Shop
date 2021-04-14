<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if message?has_content>
        <div class="alert alert-success" role="alert">${message}</div></#if>
    <div class="row my-4" id="card-container">
        <#list weak as day>
            <div class="col-sm-3 my-3">
                <div class="card">
                    <div class="card-header text-center">${day}</div>
                    <div class="card-body text-center">
                        <#list [12, 14, 16 ,18] as timeslot>
                            <#assign timeFlag = true>
                            <#if appointments??>
                                <#list appointments as appointment>
                                    <#if timeslot == appointment.dateTime.hour &&
                                    day.dayOfMonth = appointment.dateTime.dayOfMonth &&
                                    day.dayOfMonth == appointment.dateTime.dayOfMonth>
                                        <#assign timeFlag = false>
                                        <#assign occupiedAppointment = appointment>
                                        <#break>
                                    </#if>
                                </#list>
                            </#if>
                            <#if timeFlag == true>
                                <h5 class="card-title">${timeslot}:00 - <@spring.message "free"/></h5>
                            <#else>
                                <h5 class="card-title">${timeslot}:00 - <@spring.message "occupied"/></h5>
                                <div class="row mb-2">
                                    <h6 class="card-text">
                                        <#if .locale = "en"> ${occupiedAppointment.service.getTitleEn()}
                                        <#elseif .locale = "uk"> ${occupiedAppointment.service.getTitleUa()}
                                        </#if>
                                    </h6>
                                    <#if occupiedAppointment.isDone == true>
                                        <h6>Done</h6>
                                    <#else>
                                        <form action="/masters/schedule" method="post">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type ="hidden" name="appointmentId" value=${occupiedAppointment.id}>
                                            <button type="submit" class="btn btn-success mb-1"><@spring.message "done"/></button>
                                        </form>
                                    </#if>
                                </div>
                            </#if>
                        </#list>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@c.page>