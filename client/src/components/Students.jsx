import React, {useEffect, useState} from 'react'
import {closeWS, connectToWS, getStudents, sendHiToUser} from '../apiUtils/api'

const Students = () => {
  const [students, setStudents] = useState([])
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [userName, setUserName] = useState('')
  const [userNameToSendMessage, setUserNameToSendMessage] = useState('')

  useEffect(() => {
    loadStudents()
    return () => closeWS()
  }, [])


  const loadStudents = async () => {
    const students = await getStudents()
    setStudents(students.map(s => ({...s, name: s.name + ' from http endpoint'})))
  }

  const loginUser = () => {
    if (userName) {
      const addStudentCallback = student => setStudents([...students, student])
      const onConnect = () => setIsLoggedIn(true)
      connectToWS(userName, onConnect, addStudentCallback)
    }
  }

  const logoutUser = () => {
    closeWS()
    setIsLoggedIn(false)
  }

  const sendHi = () => {
    sendHiToUser(userNameToSendMessage)
  }

  return (
    <>
      {
        !isLoggedIn ?
          <>
            <h3 className="text-3xl font-bold underline">User name</h3>
            <input value={userName} onChange={e => setUserName(e.target.value)}/>
            <button onClick={loginUser}>Log in</button>
          </> :
          <>
            <h3>Actions</h3>
            <button onClick={logoutUser}>Log out</button>
            <br/>
            <h3>User name to receive hello</h3>
            <input value={userNameToSendMessage} onChange={e => setUserNameToSendMessage(e.target.value)}/>
            <button onClick={sendHi}>Send hi</button>
          </>
      }
      <br/>
      <br/>
      <br/>
      <h2>Students</h2>
      {students.map(({id, name}) =>
        <div key={id}>{id} - {name}</div>
      )}

      <button onClick={loadStudents}>Load Students</button>
    </>
  )
}

export default Students