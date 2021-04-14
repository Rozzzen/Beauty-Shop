<#import "/spring.ftl" as spring>
<#macro pagination sort url page>
    <#assign currentPage = page.getNumber()>
    <#assign head = page.getTotalPages() - 1>
    <#assign sortText = "&sort=">
    <div class="dropdown my-4 text-start">
        <#if url?contains("masters")>
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-bs-toggle="dropdown" aria-expanded="false">
                <@spring.message "sort"/>
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <li><a class="dropdown-item"
                       href="${url}?page=0${sortText}rating"><@spring.message "rating"/></a></li>
                <li><a class="dropdown-item"
                       href="${url}?page=0${sortText}name"><@spring.message "name"/></a></li>
                <#if sort != "id">
                    <li><a class="dropdown-item" href="${url}?page=0${sortText}id">
                            <@spring.message "default"/>
                        </a></li>
                </#if>
            </ul>
        </#if>
    </div>
    <#nested>
    <div>
        <ul class="pagination justify-content-center">
            <#if currentPage == 0>
                <li class="page-item disabled"><a class="page-link"><@spring.message "previous"/></a></li>
            <#else>
                <li class="page-item"><a class="page-link"
                                         href="${url}?page=${currentPage - 1}${sortText}${sort}"><@spring.message "previous"/></a>
                </li>
            </#if>
            <#list 0..page.getTotalPages() - 1 as i>
                <#if i == currentPage>
                    <li class="page-item disabled"><a class="page-link">${i + 1}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link" href="${url}?page=${i}${sortText}${sort}">${i + 1}</a>
                    </li>
                </#if>
            </#list>
            <#if currentPage == head>
                <li class="page-item disabled"><a class="page-link"><@spring.message "next"/></a></li>
            <#else>
                <li class="page-item"><a class="page-link"
                                         href="${url}?page=${currentPage + 1}${sortText}${sort}">
                        <@spring.message "next"/></a></li>
            </#if>
        </ul>
    </div>
</#macro>