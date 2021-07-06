<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>User editor</h5>
    <form action="/user" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="hidden" name="userId" value="${user.id}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" value="${user.username}"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Is Active:</label>
            <div class="col-sm-6">
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" name="isActive" ${user.active?string("checked", "")}/>
                </label>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Roles:</label>
            <div class="col-sm-6">
                <#list roles as role>
                    <div class="form-check">
                        <label class="form-check-label">
                            <input type="checkbox" class="form-check-input"
                                   name=${role} ${user.roles?seq_contains(role)?string("checked", "")}/>
                            ${role}
                        </label>
                    </div>
                </#list>
            </div>
        </div>
        <button type=submit class="btn btn-primary">Save</button>
    </form>
</@c.page>