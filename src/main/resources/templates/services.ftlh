<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="p-5 text-center mt-3 mb-3">
        <h1 class="mb-3"><@spring.message "services"/></h1>
        <#if masters??>
            <div class="dropdown my-4 text-start">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                        data-bs-toggle="dropdown" aria-expanded="false">
                    <@spring.message "filter.by.masters"/>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <#list masters as master>
                        <li><a class="dropdown-item"
                               href="/services/${master.id}">${master.userInfo.name} ${master.userInfo.surname}</a></li>
                    </#list>
                    <#if id?has_content>
                        <li><a class="dropdown-item" href="/services">
                                <@spring.message "default"/>
                            </a></li>
                    </#if>
                </ul>
            </div>
        </#if>
        <div class="row my-4" id="card-container">
            <#list services as service>
                <div class="col-sm-4 my-3">
                    <div class="card" style="width: 18rem;">
                        <div class="card-header">
                            <#if service.key.name() == "HAIRDO"><@spring.message "hairdo"/>
                            <#elseif service.key.name() == "MANICURE"><@spring.message "manicure"/>
                            <#elseif service.key.name() == "PEDICURE"><@spring.message "pedicure"/>
                            <#elseif service.key.name() == "MAKEUP"><@spring.message "makeup"/>
                            </#if>
                        </div>
                        <#list service.value as value>
                            <#if url?contains("create-order")>
                                <form action="/create-order/services" method="post">
                                    <button class="btn btn-link p-0 text-decoration-none" name="serviceId" value="${value.id}">
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">${value.title} - ${value.price} UAH</li>
                                        </ul>
                                    </button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            <#else>
                                <a class = "text-decoration-none" href="/create-order?serviceId=${value.id}">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">${value.title} - ${value.price} UAH</li>
                                    </ul>
                                </a>
                            </#if>
                        </#list>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</@c.page>