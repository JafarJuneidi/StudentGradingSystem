import React, {createContext, useCallback, useContext, useState} from "react";

export type User = {
    id: string;
    username: string;
    role: string;
};

interface AuthContextType {
    user: User | null;
    signIn: (username: string, password: string) => Promise<void>;
    signOut: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};

export const AuthProvider: React.FC<{ children: any}> = ({ children }) => {
    const [user, setUser] = useState<User | null>(null);

    const signIn = useCallback(async (username: string, password: string) => {
        const response = await fetch("http://127.0.0.1:8080/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username,
                password
            })
        });
        if (response.ok) {
            const data: User = await response.json();
            setUser(data);
        }
    }, []);

    const signOut = useCallback(() => {
        setUser(null);
    }, []);

    return (
        <AuthContext.Provider value={{ user, signIn, signOut }}>
            {children}
        </AuthContext.Provider>
    );
};