<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<#import "layout/pagination.ftl" as p>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <div class="p-3 text-center mb-3">
        <#if !url?contains("create-order")>
            <div class="dropdown my-4 text-start">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                        data-bs-toggle="dropdown" aria-expanded="false">
                    <@spring.message "filter"/>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <#list services as service>
                        <li><a class="dropdown-item"
                               href="/masters/${service?lower_case}?page=0&sort=rating">
                                <#if service == "HAIRDO"><@spring.message "hairdo"/>
                                <#elseif service == "MANICURE"><@spring.message "manicure"/>
                                <#elseif service == "PEDICURE"><@spring.message "pedicure"/>
                                <#elseif service == "MAKEUP"><@spring.message "makeup"/>
                                </#if>
                            </a>
                        </li>
                    </#list>
                    <#if !url?ends_with("masters")>
                        <li><a class="dropdown-item" href="/masters?page=0&sort=id">
                                <@spring.message "default"/>
                            </a></li>
                    </#if>
                </ul>
            </div>
        </#if>
        <@p.pagination sort url page>
            <div class="row my-4" id="card-container">
                <#list page.content as master>
                    <div class="col-sm-4 my-3">
                        <div class="card">
                            <h3 class="card-header">${master.userInfo.name} ${master.userInfo.surname}</h3>
                            <div class="card-body">
                                <img src="/img/uploads/${master.image}" class="card-img-top"
                                     alt="${master.userInfo.name}"/>
                                <ul class="list-group">
                                    <h3 class="list-group-item"><@spring.message "rating"/>: ${master.rating.averageRating?truncate(3, '')}★
                                    </h3>
                                    <li class="list-group-item">
                                        <a href="/masters/info/${master.id}"
                                           class="btn btn-primary mt-3 px-4">
                                            <@spring.message "detail.information"/>
                                        </a>
                                    </li>
                                </ul>
                                <div class="col text-center">
                                    <#if url?contains("create-order")>
                                        <form action="/create-order/masters" method="post">
                                            <button name="masterId" value="${master.id}"
                                                    class="btn btn-primary mt-3 px-4"><@spring.message "sign.up.to.this.master"/>
                                            </button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        </form>
                                    <#else>
                                        <a href="/create-order?masterId=${master.id}"
                                           class="btn btn-primary mt-3 px-4"><@spring.message "sign.up.to.this.master"/>
                                        </a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </@p.pagination>
    </div>
</@c.page>