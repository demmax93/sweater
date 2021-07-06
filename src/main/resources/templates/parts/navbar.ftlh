<#include "security.ftlh">
<#import "authorization.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Sweater</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if !known>
                <li class="nav-item">
                    <#if springMacroRequestContext.requestUri?contains("/login")>
                        <a class="nav-link active" aria-current="page" href="/login">Login</a>
                    <#else><a class="nav-link" href="/login">Login</a>
                    </#if>
                </li>
            </#if>
            <#if user??>
                <li class="nav-item">
                    <#if springMacroRequestContext.requestUri?contains("/main")>
                        <a class="nav-link active" aria-current="page" href="/main">Messages</a>
                    <#else><a class="nav-link" href="/main">Messages</a>
                    </#if>
                </li>
                <li class="nav-item">
                    <#if springMacroRequestContext.requestUri?contains("/user-messages")>
                        <a class="nav-link active" aria-current="page" href="/user-messages/${currentUserId}">My messages</a>
                    <#else><a class="nav-link" href="/user-messages/${currentUserId}">My messages</a>
                    </#if>
                </li>
                <li class="nav-item">
                    <#if springMacroRequestContext.requestUri?contains("/user/profile")>
                        <a class="nav-link active" aria-current="page" href="/user/profile">Profile</a>
                    <#else><a class="nav-link" href="/user/profile">Profile</a>
                    </#if>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <#if springMacroRequestContext.requestUri?contains("/user")>
                        <a class="nav-link active" aria-current="page" href="/user">User list</a>
                    <#else><a class="nav-link" href="/user">User list</a>
                    </#if>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3">${name}</div>
        <#if known>
            <@l.logout />
        </#if>
    </div>
</nav>