<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content><div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if message?has_content><div class="alert alert-success" role="alert">${message}</div></#if>
    <div class="p-5 text-center mt-3 mb-3">
        <div class="p-5 text-center">
            <h1 class="mb-3"><@spring.message "hello"/></h1>
            <a class="btn btn-lg btn-primary" href="/create-order" role="button"><@spring.message "sign.up"/></a>
        </div>
    </div>
</@c.page>