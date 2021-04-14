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
                <div class="mb-5">
                    <label class="form-label" for="ratingSelect"><@spring.message "rating"/></label>
                    <select class="form-control" id="ratingSelect" name="rating">
                        <#list [1, 2, 3, 4, 5] as rating>
                            <option>${rating}</option>
                        </#list>
                    </select>
                    <div class="form-group">
                        <label class="form-label" for="commentaryField"><@spring.message "commentrary"/></label>
                        <textarea class="form-control  ${(textError??)?string('is-invalid', '')}"
                                  id="commentaryField" rows="3" name="text"></textarea>
                        <#if textError??>
                            <div class="invalid-feedback">
                                ${textError}
                            </div>
                        </#if>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><@spring.message "submit.review"/></button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
</@c.page>