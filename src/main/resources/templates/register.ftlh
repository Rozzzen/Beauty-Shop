<#import "layout/common.ftlh" as c>
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content><div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="mx-auto w-25 my-5">
        <form method="post" action="register">
            <div class="row mb-3">
                <div class="form-group col-md-6">
                    <label for="name" class="form-label"><@spring.message "first.name"/></label>
                    <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}"
                           id="name" name="name" value="<#if user??>${user.name}</#if>">
                    <#if nameError??>
                        <div class="invalid-feedback">
                            ${nameError}
                        </div>
                    </#if>
                </div>
                <div class="form-group col-md-6">
                    <label for="surname" class="form-label"><@spring.message "last.name"/></label>
                    <input type="text" class="form-control ${(surnameError??)?string('is-invalid', '')}"
                           id="surname" name="surname" value="<#if user??>${user.surname}</#if>">
                    <#if surnameError??>
                        <div class="invalid-feedback">
                            ${surnameError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label"><@spring.message "email"/></label>
                <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}"
                       id="email" name="email" value="<#if user??>${user.email}</#if>">
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
                <div id="emailHelp" class="form-text"><@spring.message "email.text"/></div>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label"><@spring.message "password"/></label>
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" id="password" name="password">
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
            <div class="mb-3">
                <label for="repeatPassword" class="form-label"><@spring.message "repeat.password"/></label>
                <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}" id="repeatPassword" name="password2">
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary mb-3"><@spring.message "register"/></button>
        </form>
    </div>
</@c.page>