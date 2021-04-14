<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="p-5 text-center mt-3 mb-3">
        <div class="p-5 text-center">
            <h1 class="mb-3"><@spring.message "timeslot"/></h1>
            <form action="${url}" method="POST">
                <input class="form-control" id="date"
                       value="" type="date" required name="date"/>
                <div class="mt-2">
                    <button type="submit" class="btn btn-success"><@spring.message "select.date"/></button>
                </div>
                <#if url?contains("admin")><input type="hidden" name="appointmentId" value="${id}"/></#if>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <#if appointment.date?? || date??>
                <div class="mt-2"><@spring.message "aviable.timeslots"/>:</div>
                <form action="${url}" method="POST">
                    <#list [12, 14, 16 ,18] as timeslot>
                        <#assign timeFlag = true>
                        <#if appointment.master??>
                            <#if url?contains("/admin/appointments/select-timeslot")>
                                <#list appointment.master.appointments as time>
                                    <#if timeslot == time.dateTime.hour &&
                                    date.dayOfMonth = time.dateTime.dayOfMonth &&
                                    date.month == time.dateTime.month>
                                        <#assign timeFlag = false>
                                        <#break>
                                    </#if>
                                </#list>
                            <#else>
                                <#list appointment.master.appointments as time>
                                    <#if timeslot == time.dateTime.hour &&
                                    appointment.date.dayOfMonth = time.dateTime.dayOfMonth &&
                                    appointment.date.month == time.dateTime.month>
                                        <#assign timeFlag = false>
                                        <#break>
                                    </#if>
                                </#list>
                            </#if>
                        </#if>
                        <#if timeFlag == true>
                            <button name="time" value="${timeslot}:00" type="submit" class="btn btn-success">${timeslot}:00
                            </button>
                        </#if>
                    </#list>
                    <#if url?contains("admin")><input type="hidden" name="date"
                                                      value="${date.year?string.computer}-<#if date.month.value?length == 1>0</#if>${date.month.value}-${date.dayOfMonth}"/></#if>
                    <#if url?contains("admin")><input type="hidden" name="appointmentId" value="${id}"/></#if>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </#if>
        </div>
    </div>
    <script>
        const today = new Date();
        document.getElementById('date').min = today.toISOString().split("T")[0];
        today.setFullYear(today.getFullYear() + 1);
        document.getElementById('date').max = today.toISOString().split("T")[0];
    </script>
</@c.page>