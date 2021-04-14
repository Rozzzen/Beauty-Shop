<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if message?has_content>
        <div class="alert alert-success" role="alert">${message}</div></#if>
    <div class="mx-auto w-25 my-5">
        <form method="post" action="/masters/image" enctype="multipart/form-data">
            <div class="mb-3">
                <label class="form-label" for="customFile"><@spring.message "profile.picture"/></label>
                <input type="file" class="form-control" id="customFile" required name="file"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary mt-3"><@spring.message "submit"/></button>
            </div>
        </form>
    </div>
</@c.page>