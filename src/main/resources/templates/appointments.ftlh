<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<#import "layout/pagination.ftl" as p>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if message?has_content>
        <div class="alert alert-success" role="alert">${message}</div></#if>
    <table class="table table-hover table-responsive mt-5">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><@spring.message "date"/></th>
            <th scope="col"><@spring.message "user"/></th>
            <th scope="col"><@spring.message "user.email"/></th>
            <th scope="col"><@spring.message "master"/></th>
            <th scope="col"><@spring.message "service"/></th>
            <th scope="col"><@spring.message "done"/></th>
            <th scope="col"><@spring.message "actions"/></th>
        </tr>
        </thead>
        <tbody>
        <@p.pagination sort url page>
            <#list page.content as appointment>
                <tr>
                    <th scope="row">${appointment.id}</th>
                    <td>${appointment.dateTime.toString()?replace("T", " ")}</td>
                    <td>${appointment.user.name} ${appointment.user.surname}</td>
                    <td>${appointment.user.email}</td>
                    <td>${appointment.master.userInfo.name} ${appointment.master.userInfo.surname}</td>
                    <td>${appointment.service.title}</td>
                    <td><#if appointment.isDone == true>
                            <@spring.message "yes"/>
                        <#elseif appointment.isDone == false>
                            <@spring.message "no"/>
                        </#if></td>
                    <td><#if appointment.isDone == false>
                            <form action="/admin/appointments/cancel" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button name="appointmentId" value="${appointment.id}" type="submit"
                                        class="btn btn-warning"><@spring.message "cancel.appointment"/></button>
                            </form>
                            <form action="/admin/appointments/select-timeslot" method="get">
                                <button name="appointmentId" value="${appointment.id}" type="submit"
                                        class="btn btn-success"><@spring.message "change.timeslot"/></button>
                            </form>
                        </#if>
                    </td>
                </tr>
            </#list>
        </@p.pagination>
        </tbody>
    </table>
</@c.page>