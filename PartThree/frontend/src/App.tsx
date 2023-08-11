import React, {createContext, useContext, useEffect, useState} from 'react';
import {Link, Redirect, Route, Switch, useLocation, useRoute} from "wouter";
import {AuthProvider, useAuth} from "./AuthContext";

type User = {
    id: string,
    name: string,
    role: string
};

const AuthenticatedRoute: React.FC<{ path: string, children: any }> = ({ path, children }) => {
    const { user } = useAuth();
    const [matches] = useRoute(path);

    if (!matches) return null;

    return user ? <>{children}</> : <Redirect to="/login" />;
};

const App: React.FC = () => {
    return (
        <AuthProvider>
            <Switch>
                <Route path="/login">{() => <Login />}</Route>
                <div className="flex">
                    <Navbar />
                    <div className="flex-1 p-4">
                        <AuthenticatedRoute path="/students">
                            <GetStudents />
                        </AuthenticatedRoute>
                        <AuthenticatedRoute path="/users">
                            <GetUsers />
                        </AuthenticatedRoute>
                        <AuthenticatedRoute path="/courses">
                            <GetCourses />
                        </AuthenticatedRoute>
                        <AuthenticatedRoute path="/grades">
                            <GetGrades />
                        </AuthenticatedRoute>
                        <AuthenticatedRoute path="/addUser">
                            <AddUser />
                        </AuthenticatedRoute>
                        <AuthenticatedRoute path="/addGrade">
                            <AddGrade />
                        </AuthenticatedRoute>
                        <AuthenticatedRoute path="/addCourse">
                            <AddCourse />
                        </AuthenticatedRoute>
                    </div>
                </div>
            </Switch>
        </AuthProvider>
    );
};

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const { signIn } = useAuth();
    const [, navigate] = useLocation();

    const handleLogin = async () => {
        try {
            await signIn(username, password);
            setTimeout(() => navigate("/courses"), 500);
        } catch (error) {
            setErrorMessage("Invalid Username or Password")
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-200 py-12 px-4 sm:px-6 lg:px-8">
            <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-sm">
                <h2 className="mb-6 text-center text-2xl font-bold text-gray-700">
                    Sign in to your account
                </h2>
                <form className="space-y-4" action="#" method="POST">
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-600">
                            Username
                        </label>
                        <input
                            id="username"
                            name="username"
                            type="text"
                            autoComplete="username"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter username"
                            value={username}
                            onChange={e => setUsername(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-600">
                            Password
                        </label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            autoComplete="current-password"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter password"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="flex justify-end">
                        <button
                            type="button"
                            onClick={handleLogin}
                            className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                        >
                            Sign In
                        </button>
                    </div>
                    {errorMessage && (
                        <p className="mt-4 text-sm text-center text-red-500">
                            {errorMessage}
                        </p>
                    )}
                </form>
            </div>
        </div>
    );
};

const Navbar: React.FC = () => {
    const {user, signOut} = useAuth();
    const handleLogout = () => {
        signOut();
    }

    return (
        <div className="bg-gray-200 min-h-screen w-64 p-4">
            <div className="bg-white p-8 rounded-lg shadow-xl">
                <h2 className="mb-6 text-center text-2xl font-bold text-gray-700">
                    Navigation
                </h2>
                <nav className="space-y-4">
                    {}
                    {(() => {
                        switch(user?.role) {
                            case 'Admin':
                                return (
                                    <>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/students">Get All Students</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/users">Get All Users</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/courses">Get All Courses</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/grades">Get All Grades</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/addUser">Add User</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/addGrade">Add Grade</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/addCourse">Add Course</Link>
                                    </>
                                );
                            case 'Instructor':
                                return (
                                    <>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/students">Get All students</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/courses">Get My Courses</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/grades">Get All Grades</Link>
                                    </>
                                );
                            case 'Student':
                                return (
                                    <>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/courses">Get All Courses</Link>
                                        <Link className="block px-4 py-2 text-indigo-600 hover:bg-indigo-100 rounded-md" href="/grades">Get My Grades</Link>
                                    </>
                                );
                            default:
                                return null;
                        }
                    })()}
                </nav>
                <div className="mt-8">
                    <button className="block px-4 py-2 text-red-600 hover:bg-red-100 rounded-md" onClick={handleLogout}>Logout</button>
                </div>
            </div>
        </div>
    );
};

type Student = {
    id: string,
    name: string,
    numberOfCourses: string,
    average: string
}

const GetStudents: React.FC<{}> = () => {
    const [students, setStudents] = useState<Student[] | null>(null);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [studentToDelete, setStudentToDelete] = useState<string | null>(null);

    useEffect(() => {
        const getStudents = async () => {
            try {
                const response = await fetch("http://127.0.0.1:8080/students");

                if (response.ok) {
                    setStudents(await response.json());
                }
            } catch (error) {
                console.log(error);
            }
        }

        getStudents();
    }, []);

    const handleDeletionClick = (studentId: string) => {
        setStudentToDelete(studentId);
        setShowModal(true);
    };

    const handleConfirm = async () => {
        if (studentToDelete) {
            const response = await fetch(`http://127.0.0.1:8080/user/${studentToDelete}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                setSuccessMessage("Student deleted successfully");
                setStudents(students => students!.filter(student => student.id !== studentToDelete));
            } else {
                setErrorMessage("Failed to delete user");
            }
        }
        setShowModal(false);
        setStudentToDelete(null);
    };

    const handleCancel = () => {
        setShowModal(false);
        setStudentToDelete(null);
    };

    if (!students) {
        return <p className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">Loading...</p>;
    }

    return (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg p-4 ml-72 mt-4 w-2/3">
            <table className="min-w-full divide-y divide-gray-200">
                <thead>
                <tr>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        ID
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Name
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        No. Courses
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Average
                    </th>
                    <th className="px-6 py-3 bg-gray-50">
                        Action
                    </th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {students.map((student) => (
                    <tr key={student.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {student.id}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {student.name}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {student.numberOfCourses}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {student.average}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap flex justify-center items-center">
                            <span className="cursor-pointer text-red-500 hover:text-red-700" onClick={() => handleDeletionClick(student.id)}>X</span>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <ConfirmModal
                show={showModal}
                onConfirm={handleConfirm}
                onCancel={handleCancel}
                message="Do you want to delete this student?"
            />
        </div>
    );
}

type Course = {
    courseId: string,
    courseName: string,
    courseInstructor: string,
    numberOfStudents: string,
    courseAverage: string
}

const GetCourses: React.FC = () => {
    const [courses, setCourses] = useState<Course[] | null>(null);

    useEffect(() => {
        const getCourses = async () => {
            try {
                const response = await fetch("http://127.0.0.1:8080/courses");

                if (response.ok) {
                    setCourses(await response.json());
                }
            } catch (error) {
                console.log(error);
            }
        }

        getCourses();
    }, []);

    if (!courses) {
        return <p className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">Loading...</p>;
    }

    return (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg p-4 ml-72 mt-4 w-2/3">
            <table className="min-w-full divide-y divide-gray-200">
                <thead>
                <tr>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Course ID
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Course Name
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Course Instructor
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        No. Students
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        courseAverage
                    </th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {courses.map((course) => (
                    <tr key={course.courseId}>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {course.courseId}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {course.courseName}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {course.courseInstructor}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {course.numberOfStudents}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {course.courseAverage}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

type Grade = {
    studentName: string,
    courseName: string,
    instructorName: string,
    grade: string,
}

const GetGrades: React.FC = () => {
    const [grades, setGrades] = useState<Grade[] | null>(null);
    const { user } = useAuth();

    useEffect(() => {
        const getGrades = async (studentId: string | null, courseId: string | null) => {
            try {
                const response = await fetch("http://127.0.0.1:8080/grades", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        studentId,
                        courseId
                    })
                });

                if (response.ok) {
                    setGrades(await response.json());
                }
            } catch (error) {
                console.log(error);
            }
        }

        switch (user?.role) {
            case "Admin":
            case "Instructor": {
                getGrades(null, null);
                break;
            }
            case "Student": {
                getGrades(user?.id, null);
                break;
            }
        }
    }, [user?.id, user?.role]);

    if (!grades) {
        return <p className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">Loading...</p>;
    }

    return (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg p-4 ml-72 mt-4 w-2/3">
            <table className="min-w-full divide-y divide-gray-200">
                <thead>
                <tr>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Student Name
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Course Name
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Instructor Name
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Grade
                    </th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {grades.map((grade, index) => (
                    <tr key={index}>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {grade.studentName}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {grade.courseName}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {grade.instructorName}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {grade.grade}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

const AddUser: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [role, setRole] = useState<string>('3');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const response = await fetch("http://127.0.0.1:8080/user", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username,
                password,
                role
            })
        })

        if (!response.ok) {
            setErrorMessage("Failed to add user");
        } else {
            setSuccessMessage("Added user successfully")
        }
    };

    return (
        <div className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-sm">
                <h2 className="mb-6 text-center text-2xl font-bold text-gray-700">
                    Add New User
                </h2>
                <form className="space-y-4" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-600">
                            Username
                        </label>
                        <input
                            id="username"
                            name="username"
                            type="text"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter username"
                            value={username}
                            onChange={e => setUsername(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-600">
                            Password
                        </label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter password"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="role" className="block text-sm font-medium text-gray-600">
                            Role
                        </label>
                        <select
                            id="role"
                            name="role"
                            value={role}
                            onChange={e => setRole(e.target.value)}
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                        >
                            <option value="3">Student</option>
                            <option value="2">Instructor</option>
                            <option value="1">Admin</option>
                        </select>
                    </div>
                    <div className="flex justify-end">
                        <button
                            type="submit"
                            className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                        >
                            Add User
                        </button>
                    </div>
                    {errorMessage && (
                        <p className="mt-4 text-sm text-center text-red-500">
                            {errorMessage}
                        </p>
                    )}
                    {successMessage && (
                        <p className="mt-4 text-sm text-center text-blue-500">
                            {successMessage}
                        </p>
                    )}
                </form>
            </div>
        </div>
    );
};

const GetUsers: React.FC = () => {
    const [users, setUsers] = useState<User[] | null>(null);

    useEffect(() => {
        const getUsers = async () => {
            try {
                const response = await fetch("http://127.0.0.1:8080/users");

                if (response.ok) {
                    setUsers(await response.json());
                }
            } catch (error) {
                console.log(error);
            }
        }

        getUsers();
    }, []);

    if (!users) {
        return <p className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">Loading...</p>;
    }

    return (
        <div className="bg-white shadow overflow-hidden sm:rounded-lg p-4 ml-72 mt-4 w-2/3">
            <table className="min-w-full divide-y divide-gray-200">
                <thead>
                <tr>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        ID
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Name
                    </th>
                    <th className="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Role
                    </th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {users.map((user) => (
                    <tr key={user.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {user.id}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {user.name}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                            {user.role}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

type ConfirmModalProps = {
    show: boolean;
    onConfirm: () => void;
    onCancel: () => void;
    message?: string;
};
const ConfirmModal: React.FC<ConfirmModalProps> = ({ show, onConfirm, onCancel, message = "Are you sure?" }) => {
    if (!show) return null;

    return (
        <div className="fixed z-10 inset-0 overflow-y-auto">
            <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                <div className="fixed inset-0 transition-opacity" aria-hidden="true">
                    <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
                </div>
                <span className="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
                <div className="inline-block align-bottom bg-white rounded-lg px-4 pt-5 pb-4 text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full sm:p-6">
                    <div className="sm:items-start">
                        <div className="mt-3 sm:mt-0 sm:ml-4 sm:text-left">
                            <h3 className="text-lg leading-6 font-medium text-gray-900" id="modal-headline">
                                Confirm Action
                            </h3>
                            <div className="mt-2">
                                <p className="text-sm text-gray-500">
                                    {message}
                                </p>
                            </div>
                        </div>
                    </div>
                    <div className="mt-5 sm:mt-6 sm:grid sm:grid-cols-2 sm:gap-3 sm:grid-flow-row-dense">
                        <button
                            type="button"
                            className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-red-600 text-base font-medium text-white hover:bg-red-700 focus:outline-none sm:col-start-2 sm:text-sm"
                            onClick={onConfirm}
                        >
                            Confirm
                        </button>
                        <button
                            type="button"
                            className="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none sm:mt-0 sm:col-start-1 sm:text-sm"
                            onClick={onCancel}
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

const AddGrade: React.FC = () => {
    const [studentId, setStudentId] = useState<string>('');
    const [courseId, setCourseId] = useState<string>('');
    const [grade, setGrade] = useState<string>('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const response = await fetch("http://127.0.0.1:8080/grade", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                studentId,
                courseId,
                grade
            })
        })

        if (!response.ok) {
            setErrorMessage("Failed to add grade");
        } else {
            setSuccessMessage("Added grade successfully")
        }
    };

    return (
        <div className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-sm">
                <h2 className="mb-6 text-center text-2xl font-bold text-gray-700">
                    Add New Grade
                </h2>
                <form className="space-y-4" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="studentId" className="block text-sm font-medium text-gray-600">
                            StudentId
                        </label>
                        <input
                            id="studentId"
                            name="studentId"
                            type="text"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter studentId"
                            value={studentId}
                            onChange={e => setStudentId(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="courseId" className="block text-sm font-medium text-gray-600">
                            CourseId
                        </label>
                        <input
                            id="courseId"
                            name="courseId"
                            type="text"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter courseId"
                            value={courseId}
                            onChange={e => setCourseId(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="grade" className="block text-sm font-medium text-gray-600">
                            Grade
                        </label>
                        <input
                            id="grade"
                            name="grade"
                            type="text"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter grade"
                            value={grade}
                            onChange={e => setGrade(e.target.value)}
                        />
                    </div>
                    <div className="flex justify-end">
                        <button
                            type="submit"
                            className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                        >
                            Add Grade
                        </button>
                    </div>
                    {errorMessage && (
                        <p className="mt-4 text-sm text-center text-red-500">
                            {errorMessage}
                        </p>
                    )}
                    {successMessage && (
                        <p className="mt-4 text-sm text-center text-blue-500">
                            {successMessage}
                        </p>
                    )}
                </form>
            </div>
        </div>
    );
};

const AddCourse: React.FC = () => {
    const [courseName, setCourseName] = useState<string>('');
    const [instructorId, setInstructorId] = useState<string>('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const response = await fetch("http://127.0.0.1:8080/course", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                courseName,
                instructorId
            })
        })

        if (!response.ok) {
            setErrorMessage("Failed to add course");
        } else {
            setSuccessMessage("Added course successfully")
        }
    };

    return (
        <div className="flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-sm">
                <h2 className="mb-6 text-center text-2xl font-bold text-gray-700">
                    Add New Course
                </h2>
                <form className="space-y-4" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="courseName" className="block text-sm font-medium text-gray-600">
                            CourseName
                        </label>
                        <input
                            id="courseName"
                            name="courseName"
                            type="text"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter courseName"
                            value={courseName}
                            onChange={e => setCourseName(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="instructorId" className="block text-sm font-medium text-gray-600">
                            InstructorId
                        </label>
                        <input
                            id="instructorId"
                            name="instructorId"
                            type="text"
                            required
                            className="mt-1 w-full px-4 py-2 border rounded-md focus:outline-none focus:border-indigo-500"
                            placeholder="Enter instructorId"
                            value={instructorId}
                            onChange={e => setInstructorId(e.target.value)}
                        />
                    </div>
                    <div className="flex justify-end">
                        <button
                            type="submit"
                            className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                        >
                            Add Course
                        </button>
                    </div>
                    {errorMessage && (
                        <p className="mt-4 text-sm text-center text-red-500">
                            {errorMessage}
                        </p>
                    )}
                    {successMessage && (
                        <p className="mt-4 text-sm text-center text-blue-500">
                            {successMessage}
                        </p>
                    )}
                </form>
            </div>
        </div>
    );
};

export default App;