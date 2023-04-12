import './styles/LoginPage.css'

export const LoginPage = () => {
    return (
        <div className="loginpage">
            <form>
                <h3>Login Here</h3>

                <label for="username">Username</label>
                <input type="text" placeholder="Email" id="username"/>

                <label for="password">Password</label>
                <input type="password" placeholder="Password" id="password"/>

                <button className="btn login-btn">
                    Log In
                </button>
            </form>

        </div>
    )
}