import { Row, Col, Image, Dropdown } from 'react-bootstrap';
import './menu.scss';
import Spain from '../img/flags/spain.png';
import UK from '../img/flags/united_kingdom.png';

function MenuEsp() {
    return(
        <Row className="menu">
            <Col md = {6} className='primera col-7'>
                <a className='col'>Home</a>
                <a className='col'>CV</a>
                <a className='col'>Proyectos</a>
            </Col>
            <Col className='flags'>
                <Image src={UK} alt='UK flag' />
                <Image src={Spain} alt='Spain flag' />
            </Col>
            <Col md={3} className='contact'>
                <Dropdown>
                    <Dropdown.Toggle id="dropdown-basic">
                        Contact
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                        <Dropdown.Item href="#/action-1">669108688</Dropdown.Item>
                        <Dropdown.Item href="https://www.linkedin.com/in/raquel-alba-espinosa">LinkedIn</Dropdown.Item>
                        <Dropdown.Item href="https://www.github.com/RaquelAlbaEspinosa">Github</Dropdown.Item>
                        <Dropdown.Item href="#/action-4">Email</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            </Col>
        </Row>
    );
}

export default MenuEsp;