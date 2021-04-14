<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if message?has_content>
        <div class="alert alert-success" role="alert">${message}</div></#if>
    <div class="mx-auto w-25 my-5">
        <form method="POST">
            <div class="mb-3">
                <label for="email" class="form-label"><@spring.message "email"/></label>
                <input type="email" class="form-control" id="email" required name="email">
            </div>
            <p><@spring.message "specialities"/></p>
            <#list specialities as speciality>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name = "${speciality?string?lower_case}" value="${speciality?string?lower_case}" id="flexCheckDefault">
                    <label class="form-check-label" for="flexCheckDefault">
                        <#if speciality == "HAIRDO"><@spring.message "hairdo"/>
                        <#elseif speciality == "MANICURE"><@spring.message "manicure"/>
                        <#elseif speciality == "PEDICURE"><@spring.message "pedicure"/>
                        <#elseif speciality == "MAKEUP"><@spring.message "makeup"/>
                        </#if>
                    </label>
                </div>
            </#list>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary mb-3">Assign</button>
        </form>
    </div>
</@c.page>