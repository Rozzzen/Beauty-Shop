<#include "security.ftlh">
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="padding-left: 7%; padding-right: 7%">
    <div class="container-fluid">
        <a class="navbar-brand" href="/"><@spring.message "beauty.shop"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <div class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="" id="dropdown09" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        <#if .locale = "en"><span class="flag-icon flag-icon-us"> </span>
                        English</a>
                    <#elseif .locale = "uk"><span class="flag-icon flag-icon-ua"> </span>
                        Українська</a>
                    </#if>
                    <div class="dropdown-menu" aria-labelledby="dropdown09">
                        <#if .locale != "en">
                            <a class="dropdown-item" href="?language=en"><span class="flag-icon flag-icon-us"></span>
                                English</a>
                        </#if>
                        <#if .locale != "uk">
                        <a class="dropdown-item" href="?language=uk"><span class="flag-icon flag-icon-ua"> </span>
                            Українська</a>
                        </#if>
                    </div>
                </div>
                <li class="nav-item mx-3">
                    <a class="nav-link active" aria-current="page"
                       href="/masters"><@spring.message "masters"/></a>
                </li>
                <li class="nav-item mx-3">
                    <a class="nav-link active" aria-current="page"
                       href="/services"><@spring.message "services"/></a>
                </li>
                <#if name != "unknown">
                    <li class="nav-item dropdown mx-3">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            ${user.name} ${user.surname}</a>
                        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <#if role == "ADMIN">
                                <li><a class="dropdown-item"
                                       href="/admin/appointments"><@spring.message "manage.appointments"/></a></li>
                                <li><a class="dropdown-item"
                                       href="/admin/master-assignment"><@spring.message "assign.master.role"/></a></li>
                                <hr class="dropdown-divider"/>
                            </#if>
                            <#if role == "MASTER">
                                <li><a class="dropdown-item"
                                       href="/masters/image"><@spring.message "change.profile.picture"/></a></li>
                                <li><a class="dropdown-item"
                                       href="/masters/schedule"><@spring.message "my.schedule"/></a></li>
                                <hr class="dropdown-divider"/>
                            </#if>
                            <li>
                                <form action="/logout" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button class="dropdown-item"
                                            type="submit"><@spring.message "logout"/>
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </#if>
            </ul>
            <#if name == "unknown">
                <div class="nav-item">
                    <a href="/login" class="btn btn-success" role="button"><@spring.message "login"/></a>
                </div>
            </#if>
        </div>
    </div>
</nav>