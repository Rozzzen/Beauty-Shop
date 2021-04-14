<#import "layout/common.ftlh" as c>
<#include "layout/security.ftlh">
<#import "/spring.ftl" as spring>
<@c.page>
    <#if alert?has_content>
        <div class="alert alert-danger" role="alert">${alert}</div></#if>
    <#if message?has_content>
        <div class="alert alert-success" role="alert">${message}</div></#if>
    <div class="mx-auto w-25 my-5">
        <div class="card text-center" style="width: 20rem;">
            <h3 class="card-header">
                ${master.userInfo.name} ${master.userInfo.surname}
            </h3>
            <img src="/img/uploads/${master.image}" class="card-img-top"
                 alt="${master.userInfo.name}"/>
            <h5 class="card-title mt-2"><@spring.message "rating"/>: ${master.rating.averageRating?truncate(3, '')}★ (${master.rating.ratingCount})</h5>
            <div class="card-body">
                <h5 class="card-title"><@spring.message "specialities"/></h5>
                <ul class="list-group list-group-flush">
                    <#list master.specialities as speciality>
                        <li class="list-group-item">
                            <#if speciality == "HAIRDO"><@spring.message "hairdo"/>
                            <#elseif speciality == "MANICURE"><@spring.message "manicure"/>
                            <#elseif speciality == "PEDICURE"><@spring.message "pedicure"/>
                            <#elseif speciality == "MAKEUP"><@spring.message "makeup"/>
                            </#if>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
        <h3 class="my-3"><@spring.message "reviews"/></h3>
        <#list master.reviews as review>
            <div class="card my-2" style="width: 20rem;">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">${review.user.name} ${review.user.surname}</li>
                    <li class="list-group-item">${review.rating}★ <span
                                style="display:inline-block; width: 175px"></span>${review.createdAt}</li>
                    <li class="list-group-item">${review.text}</li>
                </ul>
            </div>
        </#list>
    </div>
</@c.page>