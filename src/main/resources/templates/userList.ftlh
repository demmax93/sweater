<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>

<@c.page>
    List of users:
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Roles</th>
            <th/>
        </tr>
        </thead>
        <tbody>
        <#list page.content as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">Edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>

<@p.pager url page />